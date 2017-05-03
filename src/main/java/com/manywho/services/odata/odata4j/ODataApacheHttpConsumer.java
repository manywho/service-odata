package com.manywho.services.odata.odata4j;

import org.odata4j.consumer.AbstractODataConsumer;
import org.odata4j.consumer.ODataClient;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.behaviors.OClientBehavior;
import org.odata4j.cxf.consumer.ODataCxfClient;
import org.odata4j.format.FormatType;

/**
 * This class is just a wrapper that disables loading CXF stuff, so that we don't break RESTEasy. It's pretty heavily
 * based on {@link org.odata4j.cxf.consumer.ODataCxfConsumer}
 */
public class ODataApacheHttpConsumer extends AbstractODataConsumer {

    private ODataCxfClient client;

    private ODataApacheHttpConsumer(FormatType type, String serviceRootUri, OClientBehavior... behaviors) {
        super(serviceRootUri);

        this.client = new ODataCxfClient(type, behaviors);
    }

    @Override
    protected ODataClient getClient() {
        return client;
    }

    public static class Builder implements ODataConsumer.Builder {

        private FormatType formatType;
        private String serviceRootUri;
        private OClientBehavior[] clientBehaviors;

        private Builder(String serviceRootUri) {
            this.serviceRootUri = serviceRootUri;
            this.formatType = FormatType.ATOM;
        }

        public Builder setFormatType(FormatType formatType) {
            this.formatType = formatType;
            return this;
        }

        public Builder setClientBehaviors(OClientBehavior... clientBehaviors) {
            this.clientBehaviors = clientBehaviors;
            return this;
        }

        public ODataApacheHttpConsumer build() {
            if (this.clientBehaviors != null) {
                return new ODataApacheHttpConsumer(this.formatType, this.serviceRootUri, this.clientBehaviors);
            } else {
                return new ODataApacheHttpConsumer(this.formatType, this.serviceRootUri);
            }
        }
    }

    public static Builder newBuilder(String serviceRootUri) {
        return new Builder(serviceRootUri);
    }

    public static ODataApacheHttpConsumer create(String serviceRootUri) {
        return ODataApacheHttpConsumer.newBuilder(serviceRootUri).build();
    }
}