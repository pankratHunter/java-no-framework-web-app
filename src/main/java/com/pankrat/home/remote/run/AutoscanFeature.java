package com.pankrat.home.remote.run;

import java.io.IOException;

import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder;
import org.glassfish.hk2.utilities.DuplicatePostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;



public class AutoscanFeature implements Feature {
    private static final Logger logger = LoggerFactory.getLogger( AutoscanFeature.class );

    @Inject
    ServiceLocator serviceLocator;


    @Override
    public boolean configure( FeatureContext featureContext )
    {
        DynamicConfigurationService dcs =
            serviceLocator.getService( DynamicConfigurationService.class );
        Populator populator = dcs.getPopulator();
        try {
            populator.populate(
                new ClasspathDescriptorFileFinder( this.getClass().getClassLoader() ),
                new DuplicatePostProcessor() );
        } catch ( IOException | MultiException ex ) {
            logger.error( ex.getMessage() );
        }
        return true;
    }
}
