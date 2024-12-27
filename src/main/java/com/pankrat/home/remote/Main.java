package com.pankrat.home.remote;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.ajp.AjpAddOn;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pankrat.home.remote.run.GrizzlyServerShutdownHookThread;
import com.pankrat.home.remote.run.LoggingConfiguration;


/**
 * Main class.
 */
public class Main {
    public static final String BASE_URI = "http://localhost:8081/home-remote";
    public static final int AJP_PORT    = 8010;
    private static final Logger logger  = LoggerFactory.getLogger( Main.class );

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main( String[] args ) throws IOException
    {
        LoggingConfiguration.setup();
        final HttpServer httpServer = getServer();

        Runtime.getRuntime().addShutdownHook(
            new GrizzlyServerShutdownHookThread( httpServer )
            );

        try {
            httpServer.start();
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
        try {
            logger.info( "Press CTRL^C to exit... " );
            Thread.currentThread().join();
        } catch ( InterruptedException e ) {
            throw new RuntimeException( e );
        }
    }

    private static HttpServer getServer()
    {
        ResourceConfig  rc         = new Application();
        HttpServer      httpServer = GrizzlyHttpServerFactory.createHttpServer( URI.create( BASE_URI ), rc );
        NetworkListener listener   =
            new NetworkListener( "grizzly", NetworkListener.DEFAULT_NETWORK_HOST, AJP_PORT );
        AjpAddOn        ajpAddon = new AjpAddOn();
        listener.registerAddOn( ajpAddon );
        httpServer.addListener( listener );
        return httpServer;
    }
}

