package com.pankrat.home.remote;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;

import com.pankrat.home.remote.appConfig.DependencyBinder;
import com.pankrat.home.remote.run.AutoscanFeature;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath( "/rest" )
public class Application extends ResourceConfig {
    public Application()
    {
        register( AutoscanFeature.class );

        //controllers only
        packages( true, "com.pankrat.home.remote.business" );

        //HK2
        register( new DependencyBinder() ); //only for factories

        // Logging.
        register( LoggingFeature.class );

        // Tracing support.
        property( ServerProperties.TRACING, TracingConfig.ON_DEMAND.name() );
        property( ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true );
        property( LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY );
        property( ServerProperties.WADL_FEATURE_DISABLE, true );
    }
}
