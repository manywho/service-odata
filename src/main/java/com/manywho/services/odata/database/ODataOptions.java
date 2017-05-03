package com.manywho.services.odata.database;

import com.manywho.sdk.api.run.elements.type.ListFilter;
import com.manywho.sdk.api.run.elements.type.ObjectDataType;
import com.manywho.sdk.api.run.elements.type.ObjectDataTypeProperty;

import java.util.stream.Collectors;

class ODataOptions {

    /**
     * Create an OData orderBy system query option, based on the values given in a ListFilter
     *
     * @param filter the ListFilter sent by the Flow
     * @return a valid OData orderBy system query option
     */
    static String createOrderBy(ListFilter filter) {
        String orderBy = "";

        if (filter.hasOrderByPropertyDeveloperName()) {
            if (filter.hasOrderByDirectionType()) {
                orderBy = String.format("%s %s", filter.getOrderByPropertyDeveloperName(), filter.getOrderByDirectionType().toLowerCase());
            } else {
                orderBy = filter.getOrderByPropertyDeveloperName();
            }
        }

        return orderBy;
    }

    /**
     * Create an OData select query option, based on the properties requested in an ObjectDataType
     *
     * @param objectDataType the ObjectDataType sent by the Flow
     * @return a valid OData select system query option
     */
    static String createSelect(ObjectDataType objectDataType) {
        return objectDataType.getProperties().stream()
                .map(ObjectDataTypeProperty::getDeveloperName)
                .collect(Collectors.joining(","));
    }
}
