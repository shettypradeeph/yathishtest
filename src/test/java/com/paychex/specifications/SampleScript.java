package com.paychex.specifications;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.testng.annotations.*;
import com.paychex.util.*;
import java.util.HashMap;
import java.util.Map;

public class SampleScript extends BaseTest {
    RestUtil restUtil;

    @BeforeClass
    public void setup() throws Exception {
        restUtil = new RestUtil();
    }

    @DataProvider(name = "dataxyz")
    public Object[][] sendData(){
        return new Object[][]{{"name","pradeep"},{"job","qa"}};
    }


    @Test
    public void getTest() {
        Map<String, Object> headers = new HashMap<String, Object>();
        Map<String, Object> parameters = new HashMap<String, Object>();
        headers.put("accept-encoding", "gzip, deflate, br");
        headers.put("accept-encoding1", "gzip, deflate, br");
        headers.put("accept-encoding2", "gzip, deflate, br");
        parameters.put("postId1", "1");
        parameters.put("postId2", "1");
        parameters.put("postId3", "1");
        restUtil.setBaseURI("https://jsonplaceholder.typicode.com");
        restUtil.setHeaders(headers);
        restUtil.setParameters(parameters);
        Response response = restUtil.getRequest("posts");
        System.out.println(response.statusCode());
        System.out.println(response.then().contentType(ContentType.JSON).extract().response().asString());
    }

    @Test(dataProvider = "dataxyz")
    public void postTest(String user, String pass) {
        Map<String, Object> headers = new HashMap<String, Object>();
        //headers.put("xyz", "lmno");

        //E:\local.properties
        PropertyLoader propertyLoader = new PropertyLoader(Utils.getFilePath("src\\test\\resources\\config.properties"));



        Map<String, Object> map = new HashMap();
        map.put("name", user);
        map.put("job",pass);
        restUtil.setJsonBody(map);

        /*JSONUtils jsonUtils = new JSONUtils();
        String json = jsonUtils.readJSONFromFile(Utils.getFilePath("/src/test/java/com/paychex/payloads/login.json"));
        restUtil.setJsonBody(json);*/
        Response response = restUtil.postRequest("api/users");
        System.out.println(response.asString());
        //System.out.println(response.then().contentType(ContentType.JSON).extract().response().asString());

        /*restUtil.setBaseURI(propertyLoader.getProperty("url"));
        restUtil.setHeaders(headers);

        //JSON Example
        JSONUtils jsonUtils = new JSONUtils();
        String json = jsonUtils.readJSONFromFile("C:/Users/pradeeps/eclipse-workspace/apitests/src/test/java/com/paychex/payloads/login.json");
        restUtil.setJsonBody(json);



        Response response = restUtil.postRequest("api/users");
        System.out.println(response.statusCode());
        System.out.println(response.then().contentType(ContentType.JSON).extract().response().asString());*/
    }

    @Test
    public void putTest() {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("content-type", "application/json");
        restUtil.setBaseURI("https://reqres.in");
        restUtil.setHeaders(headers);

        Map<String, Object> map = new HashMap();
        map.put("name", "Kabir");
        map.put("job", "Bobby");
        restUtil.setJsonBody(map);
        Response response = restUtil.putRequest("api/users/1");
        System.out.println(response.statusCode());
        System.out.println(response.then().contentType(ContentType.JSON).extract().response().asString());
    }

    @Test
    public void deleteTest() {
        restUtil.setBaseURI("https://reqres.in");
        Response response = restUtil.deleteRequest("api/users/1");
        System.out.println(response.statusCode());
    }
}
