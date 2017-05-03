package com.manywho.services.odata.types;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.api.describe.DescribeServiceRequest;
import com.manywho.sdk.api.draw.elements.type.TypeElement;
import com.manywho.sdk.services.types.TypeProvider;
import com.manywho.services.odata.ApplicationConfiguration;
import org.core4j.Enumerable;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.ODataConsumers;
import org.odata4j.core.EntitySetInfo;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.edm.EdmEntityType;
import org.odata4j.edm.EdmProperty;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ODataTypeProvider implements TypeProvider<ApplicationConfiguration> {
    // Set up the cache for the OData entity sets, as we don't want to continuously call the service
    private LoadingCache<String, Enumerable<EntitySetInfo>> entitySetCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Enumerable<EntitySetInfo>>() {
                public Enumerable<EntitySetInfo> load(String baseUrl) {
                    return ODataConsumers.create(baseUrl)
                            .getEntitySets();
                }
            });

    @Override
    public List<TypeElement> describeTypes(ApplicationConfiguration configuration, DescribeServiceRequest describeServiceRequest) {
        ODataConsumer consumer = ODataConsumers.create(configuration.getBaseUrl());

        List<TypeElement> typeElements = Lists.newArrayList();

        // Loop through each of the entity sets, and map them to a type
        for (EdmEntitySet entitySet : consumer.getMetadata().getEntitySets()) {
            EdmEntityType entityType = entitySet.getType();

            TypeElement.SimpleTypeBuilder builder = new TypeElement.SimpleTypeBuilder()
                    .setDeveloperName(entitySet.getName())
                    .setTableName(entitySet.getName());

            // Map all of the entity's properties to type properties
            for (EdmProperty property : entityType.getDeclaredProperties()) {
                ContentType contentType;

                switch (property.getType().getFullyQualifiedTypeName()) {
                    case "Edm.DateTime":
                        contentType = ContentType.DateTime;
                        break;
                    case "Edm.Double":
                        contentType = ContentType.Number;
                        break;
                    case "Edm.String":
                        contentType = ContentType.String;
                        break;
                    default:
                        throw new RuntimeException("An unsupported property type was encountered: " + property.getType().toString());
                }

                builder.addProperty(property.getName(), contentType, property.getName());
            }

            typeElements.add(builder.build());
        }

        return typeElements;
    }

    @Override
    public boolean doesTypeExist(ApplicationConfiguration configuration, String developerName) {
        // Load the entity set from the cache, and make sure the desired entity does actually exist
        return entitySetCache.getUnchecked(configuration.getBaseUrl())
                .any(set -> set.getTitle().equals(developerName));
    }
}