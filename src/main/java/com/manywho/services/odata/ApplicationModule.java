package com.manywho.services.odata;

import com.google.inject.AbstractModule;
import com.manywho.sdk.services.types.TypeProvider;
import com.manywho.services.odata.types.ODataTypeProvider;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TypeProvider.class).to(ODataTypeProvider.class);
    }
}
