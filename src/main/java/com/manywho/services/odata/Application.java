package com.manywho.services.odata;

import com.manywho.sdk.services.servers.EmbeddedServer;
import com.manywho.sdk.services.servers.Servlet3Server;
import com.manywho.sdk.services.servers.undertow.UndertowServer;
import com.manywho.services.odata.odata4j.ODataApacheHttpConsumer;
import org.odata4j.consumer.ODataConsumers;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class Application extends Servlet3Server {
    public Application() {
        // We need to override the consumer with our own custom one that doesn't try and load CXF
        System.setProperty(ODataConsumers.CONSUMERIMPL_PROPERTY, ODataApacheHttpConsumer.class.getCanonicalName());

        this.addModule(new ApplicationModule());
        this.setApplication(Application.class);
        this.start();
    }

    public static void main(String[] args) throws Exception {
        // We need to override the consumer with our own custom one that doesn't try and load CXF
        System.setProperty(ODataConsumers.CONSUMERIMPL_PROPERTY, ODataApacheHttpConsumer.class.getCanonicalName());

        EmbeddedServer server = new UndertowServer();
        server.addModule(new ApplicationModule());
        server.setApplication(Application.class);
        server.start();
    }
}
