package com.paychex.listeners;

import com.aventstack.extentreports.Status;
import com.paychex.reporting.ReportingManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext iTestContext) {
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        ReportingManager.extent.flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        ReportingManager.getNode().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        ReportingManager.getNode().fail(iTestResult.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        ReportingManager.getNode().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }
}
