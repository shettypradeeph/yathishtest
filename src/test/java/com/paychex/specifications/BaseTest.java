package com.paychex.specifications;

import com.paychex.reporting.ReportingManager;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeClass
    public void testClass(){
        ReportingManager.createTest(this.getClass().getSimpleName());
    }

    @BeforeMethod
    public void onTestStart(ITestResult iTestResult) {
        String description = iTestResult.getMethod().getDescription();
        ReportingManager.createNode(iTestResult.getMethod().getMethodName(),(description!=null ? description : ""));
    }

    @AfterSuite
    public void finalise(){
        //nononono
        ReportingManager.extent.flush();
    }
}
