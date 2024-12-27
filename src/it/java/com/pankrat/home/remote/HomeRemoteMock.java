package com.pankrat.home.remote;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;

import com.pankrat.home.remote.business.remote.resource.RemoteResource;
import com.pankrat.home.remote.run.AutoscanFeature;

public class HomeRemoteMock extends ResourceConfig {
    public HomeRemoteMock()
    {
        register( RemoteResource.class );
        register( new EndpointLoggingListener( "/" ) );
        register( AutoscanFeature.class );

        property( ServerProperties.TRACING, TracingConfig.ON_DEMAND.name() );
        property( ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true );
    }
}
