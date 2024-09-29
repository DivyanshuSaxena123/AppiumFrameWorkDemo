package org.example.TestUtils;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.utils.AppiumUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

public class iOSBaseTest extends AppiumUtils {
    public AppiumDriverLocalService service;
    public IOSDriver driver;

    // This will include all the code like Appium Server and Stop bcz this has to be done only once
    @BeforeClass
    public void iOSConfigureAppium() throws URISyntaxException, IOException {


        /*
        IMPORTANT NOTE: - If we want to run test automation on iOS real device then we have to sent
        only 2 capabilities
        1. App Path - option.setApp(""); we will not send device name and platform version it will automatically detect it
        2. We have to open web-driver project
        3. Then we have to select web driver runner and make sure we have selected "Device Connected via Cable"
        4. Make Sure we are a login apple developer account in xcode & check signing & capabilities in xcode it should be proper

        -----------------------------------------------------------------------------------------------------------

        IMPORTANT NOTE: - If we want to run test automation on Simulator These are the capabilities
        we have to send in code
        1. options.setDeviceName("");
        2. options.setApp("");
        3. option.setPlatformVersion("");

         */


        /*
        NOTE: - (How to resolve this error) Some time we run the code and found this error - XCRUN iphone simulator sdk not found
        Step 1. - Open Terminal
        Step 2. - xcode-select -p (This will xcode current path)
        Step 3. - Run these 2 commands
                 a) export DEVELOPER_DIR=/Applications/Xcode.app/Contents/Developer
                 b) export PATH="/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneSimulator.platform/Developer/usr/bin:/Applications/Xcode.app/Contents/Developer/usr/bin:$PATH"
        Step 4. - xcode-select -p (It will give - /Applications/Xcode.app/Contents/Developer)

         */


        //service = new AppiumServiceBuilder().withAppiumJS(new File("//usr//local//lib//node_modules//appium//build//lib//main.js")).withIPAddress("127.0.0.1").usingPort(4723).build();
        //service.start();
        Properties prop =  new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//org//pageObjects//Resources//data.properties");
        prop.load(fileInputStream);
        /*Now as we can below, we are getting ipAddress from DataProperties file. Now if we run the project through maven commands then remember that if we send any parameter like "ipAddress" through
         command, it will give priority to terminal "ipaddress" not the "ipAddress" we have stored in file . Now we can dynamically right a code in such using Java Ternary Operator that if we get "ipAddress"
         through then take that ipAddress otherwise fetch from dataFile
          Maven Command to send ipaddress - mvn test -PRegression -DipAddress=Any random ipaddress like 233.43.233 */
        String ipAddress = System.getProperty("ipAddress")!=null ? System.getProperty("ipAddress") : prop.getProperty("ipAddress");

        //String ipAddress = prop.getProperty("ipAddress");
        String portNo = prop.getProperty("portNo");
        service = startAppiumServer(ipAddress, Integer.parseInt(portNo));


        XCUITestOptions options = new XCUITestOptions();
        //options.setChromedriverExecutable("/Users/punchh_divyanshu/Downloads/chromedriver-mac-x64");
        //options.setDeviceName("iPhone 12 mini");
        options.setDeviceName(prop.getProperty("iOSDeviceName"));
        options.setApp("/Users/punchh_divyanshu/Library/Developer/Xcode/DerivedData/UIKitCatalog-awclvqlxxucovxaxgfhxokvvyzjw/Build/Products/Debug-iphonesimulator/UIKitCatalog.app");


        //ios we have to set a version of simulator
        options.setPlatformVersion("17.5");
        //Appium -> Web Driver Agent (WDA) -> IOS Apps
        options.setWdaLaunchTimeout(Duration.ofSeconds(2300));



        /* 3. if we are using Android device then we have to create object of android driver and if we are
        using iOS Device then we have to create object of iOS driver
        */

        /* 4. In 1st we will send the path of appium and in 2nd argument we will send the device details */
        // we can also write below line like in 2 ways
        //driver = new IOSDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver = new IOSDriver(service.getUrl(), options);

        /* This statement means that it will wait for 10sec until it found its locator if location is found
        in this time duration then it will resume the execution otherwise it will fail
         */
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2300));

    }

    // Since we use driver.quit() and server.stop() once we execute each test case so, for that we will create another method
    @AfterClass
    public void tearDown(){
        driver.quit();
        service.stop();
    }
}
