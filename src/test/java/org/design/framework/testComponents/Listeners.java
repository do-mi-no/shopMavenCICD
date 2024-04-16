package org.design.framework.testComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.design.framework.resources.ExtentReporterNG;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {

    ExtentReports extent = ExtentReporterNG.getReportObject();
    ExtentTest test;
    ThreadLocal<ExtentTest> threadLocalTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        threadLocalTest.set(test);   //assign unique thread id
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        threadLocalTest.get().log(Status.PASS, "Voila! :)");    //"threadLocalTest.get()" - is responsible to get appropriate test
    }

    @Override
    public void onTestFailure(ITestResult result) {
        threadLocalTest.get().fail(result.getThrowable());
        WebDriver drv = null;
        try {
            drv = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            throw new NoSuchElementException("Error while retrieving WebDriver from ITestResult.", e);
        }
        //https://www.udemy.com/course/selenium-real-time-examplesinterview-questions/learn/lecture/33476616#questions
        // ~12:00
        String filePath = getScreenshot(result.getMethod().getMethodName(), drv);
        threadLocalTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();     //at this moment reports are generated!
    }
}
