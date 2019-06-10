package com.paychex.specifications;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.paychex.reporting.ReportingManager;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import com.paychex.util.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Sa extends BaseTest {
    RestUtil restUtil;

    @BeforeClass
    public void setup() throws Exception {
        restUtil = new RestUtil();
    }

    @DataProvider(name = "lmno")
    public Object[][] sendData(){
        return new Object[][]{{"name","pradeep"},{"job","qa"}};
    }



    @Test
    public void getTest1() {
        ChromeDriverManager.getInstance().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("Selenium Testing");
        byte[] src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        try {
            // now copy the  screenshot to desired location using copyFile //method
            FileUtils.writeByteArrayToFile(new File("C:\\Users\\pradeeps\\eclipse-workspace\\apitests\\reporting\\error.png"),src);

                    //copyFile(src, new File("C:\\Users\\pradeeps\\eclipse-workspace\\apitests\\reporting\\error.png"));
           /* MediaEntityModelProvider mediaModel =
                    MediaEntityBuilder.createScreenCaptureFromPath("error.png").build();
            ReportingManager.getNode().fail("helloahell heo w adad asdasda asdasd sas",mediaModel);*/
            //ReportingManager.getNode().addScreenCaptureFromPath("error.png");
            ReportingManager.getNode().fail("helllllll").addScreenCaptureFromPath("error.png");
        }
        catch (Exception e){

        }

    }

    @Test(dataProvider = "lmno")
    public void getTest2(String user, String pass) {
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
    public void getTest3() {
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
    public void getTest4() {
        restUtil.setBaseURI("https://reqres.in");
        Response response = restUtil.deleteRequest("api/users/1");
        System.out.println(response.statusCode());
    }
}
