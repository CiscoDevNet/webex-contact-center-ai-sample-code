package com.cisco.wccai.grpc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;


public class LoadProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadProperties.class);
    private static final Properties prop = new Properties();

    LoadProperties()
    {

    }
    public static Properties loadProperties() {

        InputStream is;
        String propertyFileName = "config.properties";

        try {

            is = LoadProperties.class.getClassLoader().getResourceAsStream(propertyFileName);

            if(is!=null)
                prop.load(is);
            else
                throw new FileNotFoundException("property file " + propertyFileName + "not found in classpath");

        } catch (Exception ex) {
            LOGGER.warn(ex.getCause().getMessage());
        }

        return prop;
    }

}
