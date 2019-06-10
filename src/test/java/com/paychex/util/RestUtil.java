package com.paychex.util;

import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.paychex.reporting.ReportingManager;

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
        ReportingManager.getNode().info("BaseURI set to " + baseURI);
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
        ReportingManager.getNode().info("<b>Headers: </b>" + Utils.formatParams(headers));
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
        ReportingManager.getNode().info("<b>Parameters: </b>" + Utils.formatParams(parameters));
    }

    public RestUtil() {

    }

    public Response getRequest(String path) {
        Response response = RestAssured.
                given().params(getParameters()).log().all().headers(getHeaders()).get(getBaseURI() + "/" + path);
        ReportingManager.getNode().info(MarkupHelper.createCodeBlock(response.toString()));
        return response;
    }

    public Response postRequest(String path) {
        Response response = RestAssured.given().body(getJsonBody()).log().all().headers(getHeaders()).post(getBaseURI() + "/" + path);
        ReportingManager.getNode().info(MarkupHelper.createCodeBlock(response.toString()));
        return response;
    }

    public Response putRequest(String path) {
        Response response = RestAssured.given().body(getJsonBody()).log().all().headers(getHeaders()).put(getBaseURI() + "/" + path);
        ReportingManager.getNode().info(MarkupHelper.createCodeBlock(response.toString()));
        return response;
    }

    public Response deleteRequest(String path) {
        Response response = RestAssured.given().log().all().delete(getBaseURI() + "/" + path);
        ReportingManager.getNode().info(MarkupHelper.createCodeBlock(response.toString()));
        return response;
    }
}
