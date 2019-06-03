package com.paychex.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class ConfigManager {
    private Properties properties = new Properties();
    private String configFilePath;
    private final Logger log = LogManager.getLogger(ConfigManager.class);
    private static ConfigManager configManager;

    private ConfigManager() {
        configFilePath = ConfigManager.class.getClassLoader().getResource("config.properties").getPath();
    }

    public ConfigManager(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public static synchronized ConfigManager getConfig() {
        if (configManager == null) {
            configManager = new ConfigManager();
        }
        return configManager;
    }

    public static synchronized ConfigManager getConfig(String resourceName) {
        if (configManager == null) {
            configManager = new ConfigManager(resourceName);
        }
        return configManager;
    }

    public String getProperty(String key) {
        String value = "";
        if (key != "") {
            if (properties!= null)
                loadProperties(configFilePath);
            try {
                if (System.getenv(key) != null && System.getenv(key) != "") {
                    return System.getenv(key); // check for System environment variable before checking for
                    // build.properties file
                }
                if (System.getProperty(key) != null && System.getProperty(key) != "") {
                    return System.getProperty(key); // check for System property before checking for build.properties
                    // file
                }
                if (!properties.getProperty(key).trim().isEmpty())
                    value = properties.getProperty(key).trim();
            } catch (NullPointerException e) {
                Assert.fail("Key - '" + key + "' does not exist or not given a value in " + configFilePath + " file");
            }
        } else {
            log.error("key cannot be null or empty.. ");
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
        } catch (FileNotFoundException e) {
            log.error("Cannot find configuration file - " + configFilePath);
            Assert.fail("Cannot find configuration file - " + configFilePath);
        } catch (IOException e) {
            log.error("Cannot read configuration file - " + " at " + configFilePath);
            Assert.fail("Cannot read configuration file - " + " at " + configFilePath);
        }
    }
}
