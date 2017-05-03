package com.manywho.services.odata;

import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.services.configuration.Configuration;

public class ApplicationConfiguration implements Configuration {
    @Configuration.Setting(name = "Base URL", contentType = ContentType.String)
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }
}
