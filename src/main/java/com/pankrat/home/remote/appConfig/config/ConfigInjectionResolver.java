package com.pankrat.home.remote.appConfig.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pankrat.home.remote.business.remote.resource.RemoteResource;


public class ConfigInjectionResolver implements InjectionResolver<Config> {
    private static final Logger logger = LoggerFactory.getLogger( RemoteResource.class );

    @Override
    public Object resolve( Injectee injectee, ServiceHandle<?> handle )
    {
        if ( String.class == injectee.getRequiredType() )
        {
            Config annotation = injectee.getParent().getAnnotation( Config.class );
            if ( annotation != null )
            {
                String prop = annotation.value();
                return loadFromFile( prop );
            }
        }
        return null;
    }

    private String loadFromFile( String key )
    {
        String      returnValue = null;
        if ( key == null || key.isEmpty() ) return returnValue;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream( "home-remote.properties" );
        try {
            Properties props = new Properties();
            props.load( inputStream );
            returnValue = props.getProperty( key );
        } catch ( IOException ex ) {
            logger.error( "Błąd pobierania konfiguracji. " + ex );
        }
        return returnValue;
    }

    @Override
    public boolean isConstructorParameterIndicator()
    {
        return false;
    }

    @Override
    public boolean isMethodParameterIndicator()
    {
        return false;
    }
}
