package com.paychex.util;

import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PropertyLoader {

    private Properties properties = new Properties();
    private String configFilePath = null;
    private static PropertyLoader propertyLoader = null;

    public PropertyLoader() {
        configFilePath = PropertyLoader.class.getClassLoader().getResource("gabc.properties").getPath();
    }


    //reads the key values from *.properties where * is config name provided to the constructor
    public PropertyLoader(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public static synchronized PropertyLoader getConfig() {
        if(propertyLoader == null) {
            propertyLoader = new PropertyLoader();
        }
        return propertyLoader;
    }

    public static synchronized PropertyLoader getConfig(String resourceName) {
        if(propertyLoader == null) {
            propertyLoader = new PropertyLoader(resourceName);
        }
        return propertyLoader;
    }

    /**
     * Returns the value of given property from environment variables and then from System properties if not present then from build.properties file
     * @param key - Config Param value that requires to be returned from build.properties file
     * @return - return ConfigValue
     */
    public String getProperty(String key) {
        String value = "";
        if(key != "") {
            if (properties!= null)
                loadProperties(configFilePath);
            try {
                if(System.getenv(key) != null && System.getenv(key) != "") {
                    return System.getenv(key); // check for System environment variable before checking for build.properties file
                }
                if(System.getProperty(key) != null && System.getProperty(key) != "") {
                    return System.getProperty(key); // check for System property before checking for build.properties file
                }
                return properties.getProperty(key);
            }
            catch(NullPointerException e) {
                Assert.fail("Key - '" + key + "' does not exist or not given a value in " + configFilePath + " file");
            }
        }
        else {
            Assert.fail("key cannot be null or empty.. ");
        }
        return value;
    }

    private void loadProperties(String configFilePath) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(configFilePath);
            properties.load(fis);
            fis.close();
        }
        catch (FileNotFoundException e) {
            Assert.fail("Cannot find configuration file - " + configFilePath);
        }
        catch (IOException e)
        {
            Assert.fail("Cannot read configuration file - " + " at " + configFilePath);
        }
    }
}
