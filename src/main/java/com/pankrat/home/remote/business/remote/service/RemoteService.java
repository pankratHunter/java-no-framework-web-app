package  com.pankrat.home.remote.business.remote.service;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RemoteService {
    Logger logger = LoggerFactory.getLogger( RemoteService.class.getName() );

    public String callRemote()
    {
        logger.info( "remote running" );
        return "ok";
    }
}

