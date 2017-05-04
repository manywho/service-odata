package com.manywho.services.odata.odata4j;

import com.manywho.services.odata.ApplicationConfiguration;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.ODataConsumers;
import org.odata4j.consumer.behaviors.OClientBehaviors;

public class ODataConsumerFactory {
    public static ODataConsumer create(ApplicationConfiguration configuration) {
        ODataConsumer.Builder builder = ODataConsumers.newBuilder(configuration.getBaseUrl());

        if (configuration.getBasicAuthUsername() != null && configuration.getBasicAuthPassword() != null) {
            builder.setClientBehaviors(OClientBehaviors.basicAuth(
                    configuration.getBasicAuthUsername(),
                    configuration.getBasicAuthPassword()
            ));
        }

        return builder.build();
    }
}
