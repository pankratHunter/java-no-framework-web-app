package com.pankrat.home.remote.appConfig;

import com.pankrat.home.remote.appConfig.config.Config;
import com.pankrat.home.remote.appConfig.config.ConfigInjectionResolver;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@Provider
public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {

        bind(ConfigInjectionResolver.class)
                .to(new TypeLiteral<InjectionResolver<Config>>() {
                })
                .in(Singleton.class);
   }
}
