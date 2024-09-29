package org.example.TestUtils;


// As we have seen in ExtentReport Project that we have to extent.createtest() before we execute code it is tedious task to write in 100 file
// To make this dynamic we have made Listeners class

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.utils.AppiumUtils;

import java.io.IOException;

import static org.example.TestUtils.ExtentReporterNG.extent;


public class Listeners extends AppiumUtils implements ITestListener {
    ExtentTest test;
    AppiumDriver driver;
    ExtentReports extent = ExtentReporterNG.getReporterObject();

    @Override
    public void onTestStart(ITestResult iTestResult) {
        // Now we previously we are giving name for each test case now it should be dynamic iTestResult will store all the information of the test case we will the "Name" from this
        test = extent.createTest(iTestResult.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        test.log(Status.PASS, "Test Case Passed");

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        test.fail(iTestResult.getThrowable());

        /*
        Now this will get tricky bcz as u know getScreeshot method is expecting 2 argument (1. TestCase Name that should be dynamic 2. Drive it should be
        dynamic too. so to make driver here dynamic we have to get testcase driver info also & then convert into AppiumDrive
         */
        try {
            driver = (AppiumDriver) iTestResult.getTestClass().getRealClass().getField("driver").get(iTestResult.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            //addScreenCaptureFromPath expect 2 argument 1. File Path where screenshot is stored and 2. What title name of ss should be when we attach in index.html report 2 argument is optional as well
            test.addScreenCaptureFromPath(getScreenshot(iTestResult.getMethod().getMethodName(), driver), iTestResult.getMethod().getMethodName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
         extent.flush();

    }
}
