package com.pankrat.home.remote.run;

import java.io.IOException;
import java.util.logging.LogManager;

import org.slf4j.bridge.SLF4JBridgeHandler;

public final class LoggingConfiguration {
    private LoggingConfiguration()
    {
    }

    public static void setup()
    {
        LogManager.getLogManager().reset();
        loadLoggingFile();
        setupJavaUtilLoggingToSlf4jBridge();
    }

    private static void setupJavaUtilLoggingToSlf4jBridge()
    {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    private static void loadLoggingFile()
    {
        try
        {
            LogManager.getLogManager().readConfiguration(
                LoggingConfiguration.class.getClassLoader().getResourceAsStream( "logging.properties" )
                );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}

