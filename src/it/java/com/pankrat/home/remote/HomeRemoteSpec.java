package com.pankrat.home.remote;

import java.io.IOException;
import java.net.URI;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

public abstract class HomeRemoteSpec {
    protected static WebTarget target;
    private static String baseUri = "http://localhost:8090";
    private static HttpServer server;

    @BeforeAll
    public static void beforeAllTests() throws IOException
    {
        ResourceConfig rc = new HomeRemoteMock();

        rc.register( getBinder() );
        server = GrizzlyHttpServerFactory.createHttpServer(
            URI.create( "http://localhost:8090" ), rc );
        server.start();

        ClientConfig config = new ClientConfig();
        Client       client = ClientBuilder.newClient( config );
        client.register( logging() );
        target = client.target( baseUri );
    }

    @AfterAll
    public static void afterAllTests()
    {
        server.shutdown();
    }

    private static LoggingFeature logging()
    {
        Logger logger = Logger.getLogger( "org.glassfish.grizzly.http.server.HttpHandler" );

        logger.setLevel( Level.ALL );
        logger.setUseParentHandlers( false );
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel( Level.ALL );
        logger.addHandler( ch );
        return new LoggingFeature( logger, Level.INFO, null, null );
    }

    private static AbstractBinder getBinder()
    {
        return new AbstractBinder()
               {
                   @Override
                   public void configure()
                   {
                   }
               };
    }
}
