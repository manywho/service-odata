package com.manywho.services.odata;

import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.services.configuration.Configuration;

public class ApplicationConfiguration implements Configuration {
    @Configuration.Setting(name = "Base URL", contentType = ContentType.String)
    private String baseUrl;

    @Configuration.Setting(name = "Basic Authentication Username", contentType = ContentType.String)
    private String basicAuthUsername;

    @Configuration.Setting(name = "Basic Authentication Password", contentType = ContentType.Password)
    private String basicAuthPassword;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getBasicAuthUsername() {
        return basicAuthUsername;
    }

    public String getBasicAuthPassword() {
        return basicAuthPassword;
    }
}
