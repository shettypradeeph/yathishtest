package com.paychex.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import java.io.FileReader;
import java.io.IOException;


public final class JSONUtils {
    private final Logger log = LogManager.getLogger(JSONUtils.class);

    public JSONUtils() {
    }

    /**
     * This method used to read the content in a JSON file as String
     *
     * @param path JSON file path
     * @return String content of specified JSON file
     * @throws IOException    if file in the given path not found
     * @throws ParseException if the file is not a valid JSON
     */
    public String readJSONFromFile(String path) {
        JSONParser parser = new JSONParser();
        JSONObject jsonContent = null;
        try {
            jsonContent = (JSONObject) parser.parse(new FileReader(path));
            log.info("JSON content in the file "+path+" "+jsonContent);
        } catch (IOException e) {
            log.error("readJSONFromFile-- Seems file is not available " + e.getStackTrace());
            Assert.fail("readJSONFromFile-- Seems file is not available " + e.getStackTrace());
        } catch (ParseException e) {
            log.error("readJSONFromFile-- Seems like file is not a proper json " + e.getStackTrace());
            Assert.fail("readJSONFromFile-- Seems like file is not a proper json " + e.getStackTrace());
        }
        return (jsonContent != null) ? jsonContent.toJSONString() : null;
    }
}
