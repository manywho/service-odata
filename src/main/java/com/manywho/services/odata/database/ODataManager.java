package com.manywho.services.odata.database;

import com.manywho.sdk.api.run.elements.type.ListFilter;
import com.manywho.sdk.api.run.elements.type.MObject;
import com.manywho.sdk.api.run.elements.type.ObjectDataType;
import com.manywho.sdk.services.utils.Streams;
import com.manywho.services.odata.ApplicationConfiguration;
import com.manywho.services.odata.odata4j.ODataConsumerFactory;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.core.OEntity;
import org.odata4j.core.OQueryRequest;

import java.util.List;
import java.util.stream.Collectors;

import static com.manywho.services.odata.database.ODataOptions.createOrderBy;
import static com.manywho.services.odata.database.ODataOptions.createSelect;

public class ODataManager {
    public MObject find(ApplicationConfiguration configuration, ObjectDataType objectDataType, String id) {
        // Fetch the OData entity, using the entity name and key given in the request
        OEntity entity = ODataConsumerFactory.create(configuration)
                .getEntity(objectDataType.getDeveloperName(), id)
                .execute();

        return createFromEntity(entity, objectDataType);
    }

    public List<MObject> findAll(ApplicationConfiguration configuration, ObjectDataType objectDataType, ListFilter filter) {
        ODataConsumer consumer = ODataConsumerFactory.create(configuration);

        // Build the OData request, using any filters passed in by the Flow
        OQueryRequest<OEntity> oQueryRequest = consumer.getEntities(objectDataType.getDeveloperName())
                .select(createSelect(objectDataType))
                .skip(filter.getOffset())
                .top(filter.getLimit());

        // It's only valid to add an orderBy if one was provided
        String orderBy = createOrderBy(filter);
        if (!orderBy.isEmpty()) {
            oQueryRequest.orderBy(orderBy);
        }

        // Fetch the OData result, build the objects from it, and send it back
        return Streams.asStream(oQueryRequest.execute())
                .map(entity -> createFromEntity(entity, objectDataType))
                .collect(Collectors.toList());
    }

    private MObject createFromEntity(OEntity entity, ObjectDataType objectDataType) {
        // Create the basic info for the object
        MObject.SimpleMObjectBuilder mObjectBuilder = new MObject.SimpleMObjectBuilder()
                .setDeveloperName(objectDataType.getDeveloperName())
                .setExternalId(String.valueOf(entity.getEntityKey().asSingleValue()));

        // Add each of the entity property values, chosen from the properties in the type that was sent in the request
        objectDataType.getProperties().stream()
                .map(property -> entity.getProperty(property.getDeveloperName()))
                .forEach(property -> mObjectBuilder.addProperty(property.getName(), property.getValue()));

        return mObjectBuilder.build();
    }
}
