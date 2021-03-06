package com.paychex.util;


import org.testng.Assert;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class Utils {

    public static InputStream findResourceAsStream(String resourceName) {
        if (!resourceName.startsWith("/")) {
            resourceName = "/" + resourceName;
        }
        URL ret = Utils.class.getResource(resourceName);
        InputStream is = null;
        if (ret != null) {
            try {
                is = ret.openStream();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (is == null) {
            throw new RuntimeException("resourcename " + resourceName + " not found");
        }
        return is;
    }

    public static String getFilePath(String path) {
        String rootDirFile = "";
        try {
            rootDirFile = new File(".").getCanonicalPath();
        } catch (Exception e) {
            Assert.fail("File not found");
        }
        return (rootDirFile + File.separator + path);
    }

    public static String formatParams(Map<String, Object> parameters){
        String params="<br/>";
        for (String key: parameters.keySet()) {
            params = params+"<b>"+key+"</b>:  "+parameters.get(key)+"<br/>";
        }
        return params;
    }
}
