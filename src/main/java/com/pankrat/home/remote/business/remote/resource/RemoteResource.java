package com.pankrat.home.remote.business.remote.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pankrat.home.remote.business.remote.service.RemoteService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path( "/remote" )
public class RemoteResource {
    private static final Logger logger = LoggerFactory.getLogger( RemoteResource.class );

    RemoteService remoteService;

    @Inject
    public RemoteResource( RemoteService remoteService )
    {
        this.remoteService = remoteService;
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Path( "getAll" )
    public String getAll()
    {
        logger.info( "calling RemoteResource" );
        return remoteService.callRemote();
    }
}
