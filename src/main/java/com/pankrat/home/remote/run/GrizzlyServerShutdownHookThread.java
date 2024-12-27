package com.pankrat.home.remote.run;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.http.server.AddOn;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GrizzlyServerShutdownHookThread extends Thread {
    public static final String THREAD_NAME = "Grizzly Server Shutdown Hook";

    public static final int GRACE_PERIOD = 10;
    public static final TimeUnit GRACE_PERIOD_TIME_UNIT = TimeUnit.SECONDS;

    private final HttpServer server;

    private static final Logger logger = LoggerFactory.getLogger( GrizzlyServerShutdownHookThread.class );

    /**
     * @param server The server to shut down
     */
    public GrizzlyServerShutdownHookThread( HttpServer server )
    {
        this.server = server;
        setName( THREAD_NAME );
    }

    @Override
    public void run()
    {
        logger.info( "Running Grizzly Server Shutdown Hook." );
        logger.info( "Shutting down server." );

        AddOn[] addons = server.getListener( "grizzly" ).getAddOns();
        for ( AddOn addOn : addons )
        {
            server.getListener( "grizzly" ).deregisterAddOn( addOn );
        }

        GrizzlyFuture<NetworkListener> futureNetworkListner =
            server.getListener( "grizzly" ).shutdown( GRACE_PERIOD, GRACE_PERIOD_TIME_UNIT );
        GrizzlyFuture<HttpServer>      future = server.shutdown( GRACE_PERIOD, GRACE_PERIOD_TIME_UNIT );

        try {
            logger.info( "Waiting for server to shut down... Grace period is {} {} \n", GRACE_PERIOD, GRACE_PERIOD_TIME_UNIT );
            futureNetworkListner.get();
            future.get();
        } catch ( InterruptedException | ExecutionException e ) {
            logger.info( "Error while shutting down server. {}", e );
        }
        logger.info( "Server stopped." );
    }
}
