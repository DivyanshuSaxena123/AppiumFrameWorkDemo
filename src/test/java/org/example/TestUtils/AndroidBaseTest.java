package org.example.TestUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.pageObjects.Android.FormPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.utils.AppiumUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;


public class AndroidBaseTest extends AppiumUtils {


    public AppiumDriverLocalService service;
    public AndroidDriver driver;
    public FormPage formPage;


    // This will include all the code like Appium Server and Stop bcz this has to be done only once

    // Now since we have added "group" tag to "Hybrid" test case.  this file is mandatory to run testNg provide a keyword (alwaysRun = true). we will add this key to all the where it is necessary to apply
    @BeforeClass(alwaysRun = true)
    public void ConfigureAppium() throws URISyntaxException, IOException {

        // Note :- This is the appium code -> this code will be sent to appium server -> then server will interpret the code we write for mobile automation and then it will interact with device

        /* 1. Now as we can we have to manually start the appium server
        what if we write the code to start appium server yes we can */

        // As we can see this common for both iOS and Android we can put this Appium Utils

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




          /* 2. SLF4J waring is resolved when we added dependency for that in pom.xml and
        Neither ANDROID_SDK or ANDROID_HOME is exported this is resolved bcz always remember
        Latest updated version of Mac has terminal with zsh while older version support bash
        This website help me solved this - "https://kashanhaider.com/set-up-android-environment-variables-on-macos/ if after"
        entering "adb" command it shows data in terminal that it is working fine
        */

        UiAutomator2Options options = new UiAutomator2Options();
        //options.setDeviceName("Pixel 8 API 33 Emulator");// Emulator
        options.setDeviceName(prop.getProperty("androidDeviceName"));
        //options.setDeviceName("Android Device"); // real device
        options.setApp(System.getProperty("user.dir")+"//src//test//java//resources//General-Store.apk");
        options.setChromedriverExecutable("/Users/punchh_divyanshu/Downloads/chromedriver");



        /* 3. if we are using Android device then we have to create object of android driver and if we are
        using iOS Device then we have to create object of iOS driver
        */

        /* 4. In 1st we will send the path of appium and in 2nd argument we will send the device details */
        // we can also write below line like in 2 ways
        //driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver = new AndroidDriver(service.getUrl(), options);
        /* This statement means that it will wait for 10sec until it found its locator if location is found
        in this time duration then it will resume the execution otherwise it will fail
         */
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2300));
        formPage =  new FormPage(driver);

    }

    // Since we use driver.quit() and server.stop() once we execute each test case so, for that we will create another method
    @AfterClass(alwaysRun = true)
    public void tearDown(){
        driver.quit();
        service.stop();
    }

}
