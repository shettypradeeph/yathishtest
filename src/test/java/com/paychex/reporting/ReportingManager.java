package com.paychex.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.paychex.util.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ReportingManager {
    private static ExtentReports extentReports;
    private static Map extentTestMap = new HashMap();
    private static Map extentNodeMap = new HashMap();
    public static ExtentReports extent = ReportingManager.getReporter();

    public synchronized static ExtentReports getReporter() {
        if (extentReports == null) {
            String workingDir = System.getProperty("user.dir");
            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(workingDir + File.separator + "ExtentReportsTestNG.html");
            extentReports = new ExtentReports();
            htmlReporter.loadXMLConfig(Utils.getFilePath("src\\test\\resources\\reportconfig.xml"));
            extentReports.attachReporter(htmlReporter);
        }
        return extentReports;
    }

    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest getNode() {
        return (ExtentTest) extentNodeMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest createTest(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }

    public static synchronized ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }

    public static synchronized ExtentTest createNode(String testName, String desc) {
        ExtentTest test = getTest().createNode(testName, desc);
        extentNodeMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }

    public static synchronized ExtentTest createNode(String testName) {
        ExtentTest test = getTest().createNode(testName);
        extentNodeMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }
}
