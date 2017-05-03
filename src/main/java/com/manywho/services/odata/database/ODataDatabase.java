package com.manywho.services.odata.database;

import com.manywho.sdk.api.run.elements.type.ListFilter;
import com.manywho.sdk.api.run.elements.type.MObject;
import com.manywho.sdk.api.run.elements.type.ObjectDataType;
import com.manywho.sdk.services.database.RawDatabase;
import com.manywho.services.odata.ApplicationConfiguration;

import javax.inject.Inject;
import java.util.List;

public class ODataDatabase implements RawDatabase<ApplicationConfiguration> {
    private final ODataManager manager;

    @Inject
    public ODataDatabase(ODataManager manager) {
        this.manager = manager;
    }

    @Override
    public MObject create(ApplicationConfiguration configuration, MObject mObject) {
        throw new RuntimeException("Creating records using this service is not supported");
    }

    @Override
    public List<MObject> create(ApplicationConfiguration configuration, List<MObject> list) {
        throw new RuntimeException("Creating records using this service is not supported");
    }

    @Override
    public void delete(ApplicationConfiguration configuration, MObject mObject) {
        throw new RuntimeException("Deleting records using this service is not supported");
    }

    @Override
    public void delete(ApplicationConfiguration configuration, List<MObject> list) {
        throw new RuntimeException("Deleting records using this service is not supported");
    }

    @Override
    public MObject update(ApplicationConfiguration configuration, MObject mObject) {
        throw new RuntimeException("Updating records using this service is not supported");
    }

    @Override
    public List<MObject> update(ApplicationConfiguration configuration, List<MObject> list) {
        throw new RuntimeException("Updating records using this service is not supported");
    }

    @Override
    public MObject find(ApplicationConfiguration configuration, ObjectDataType objectDataType, String id) {
        return manager.find(configuration, objectDataType, id);
    }

    @Override
    public List<MObject> findAll(ApplicationConfiguration configuration, ObjectDataType objectDataType, ListFilter filter) {
        return manager.findAll(configuration, objectDataType, filter);
    }
}
