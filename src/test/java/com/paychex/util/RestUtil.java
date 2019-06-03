package com.paychex.util;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class RestUtil {
    private String baseURI;
    private String basePath;
    private Object jsonBody;

    public Object getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(Object jsonBody) {
        this.jsonBody = jsonBody;
    }

    private Map<String, Object> headers = new HashMap<String, Object>();
    private Map<String, Object> parameters = new HashMap<String, Object>();

    public String getBaseURI() {
        return baseURI;
    }

    public void setBaseURI(String baseURI) {
        this.baseURI = baseURI;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        headers.put("content-type", "application/json");
        this.headers = headers;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public RestUtil() {

    }

    public Response getRequest(String path) {
        Response response = RestAssured.
                given().params(getParameters()).log().all().headers(getHeaders()).get(getBaseURI() + "/" + path);
        return response;
    }

    public Response postRequest(String path) {
       return RestAssured.given().body(getJsonBody()).log().all().headers(getHeaders()).post(getBaseURI()+"/"+path);
    }

    public Response putRequest(String path) {
        return RestAssured.given().body(getJsonBody()).log().all().headers(getHeaders()).put(getBaseURI()+"/"+path);
    }

    public Response deleteRequest(String path) {
        return RestAssured.given().log().all().delete(getBaseURI()+"/"+path);
    }
}
