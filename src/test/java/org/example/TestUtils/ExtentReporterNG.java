package org.example.TestUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {


    static ExtentReports extent;
    // The advantage of making method static is that we don't have to create object of class we can directly use it
    public static ExtentReports getReporterObject(){
        // 1.ExtendReports - This help to create bug report.  2. ExtendSparkReporter - This help in creating html file of that report
        // Step 1 - First we will set path where in the project we want to create report and send that path to object of "ExtendSparkReport"
        String path = System.getProperty("user.dir")+"//reports//index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Report");
        reporter.config().setDocumentTitle("Web Result");


        //Step 2 = Create object for Extent Reports
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Divyanshu Saxena");
        return extent;

    }
}
