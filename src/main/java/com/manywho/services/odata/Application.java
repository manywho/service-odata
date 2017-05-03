package com.manywho.services.odata;

import com.manywho.sdk.services.servers.EmbeddedServer;
import com.manywho.sdk.services.servers.undertow.UndertowServer;
import com.manywho.services.odata.odata4j.ODataApacheHttpConsumer;
import org.odata4j.consumer.ODataConsumers;

public class Application {
    public static void main(String[] args) throws Exception {
        // We need to override the consumer with our own custom one that doesn't try and load CXF
        System.setProperty(ODataConsumers.CONSUMERIMPL_PROPERTY, ODataApacheHttpConsumer.class.getCanonicalName());

        EmbeddedServer server = new UndertowServer();
        server.addModule(new ApplicationModule());
        server.setApplication(Application.class);
        server.start();
    }
}
