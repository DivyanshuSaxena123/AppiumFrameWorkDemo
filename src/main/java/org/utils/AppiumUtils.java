package org.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;


public class AppiumUtils {
    public AppiumDriverLocalService service;
    /*
    The purpose of this class is For Android Gesture we have made separate AndroidGesture and same for iOS as well
    now suppose, there is a method that is common for both iOS and Android as well as that such a type of
    method will be stored in Appium Utils
     */

    //AppiumDriver consist both Ios Driver and Android Driver so, it will work for both

    /*
    AppiumDriver driver;

    public AppiumUtils(AppiumDriver driver){
        this.driver = driver;

    }
     */

    // As we know below method is used to remove the dollar from amount basically converting String to Double
    public Double getFormattedAmount(String amount) {
        Double price = Double.parseDouble(amount.substring(1));
        return price;
    }

    public void waitForElementtoAppear(WebElement ele, AppiumDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains((ele),"text", "Cart"));

    }

    //Method to read json data and convert to hashmap and then send hashmap List<HashMap<String><String>> to test case
    public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException {
        //Conversion of json file to json string

        // We can write like this - String jsonContent = FileUtils.readFileToString("/Users/punchh_divyanshu/Desktop/AppiumFrameworkDesignDemo/src/test/java/testData/eCommerce.json");
        //String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//src//test//java//testData//eCommerce.json"), StandardCharsets.UTF_8);
        String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
        //String jsonContent = FileUtils.readFileToString(new File(System.getProperty(jsonFilePath), StandardCharsets.UTF_8));

        ObjectMapper mapper =  new ObjectMapper();
        List<HashMap <String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
            @Override
            public String toString() {
                return super.toString();
            }
        });
        return data;

    }

    // Start Appium Server
    public AppiumDriverLocalService startAppiumServer(String ipAddress, int portNo){
        // right now ip address and port no. are hard coded we will make it dynamic
        service = new AppiumServiceBuilder().withAppiumJS(new File("//usr//local//lib//node_modules//appium//build//lib//main.js")).withIPAddress(ipAddress).usingPort(portNo).build();
        service.start();
        return service;
    }

    // If a test case fail then we will need to attach the screenshot to know where it fails
    public String getScreenshot(String testCaseName, AppiumDriver driver) throws IOException {
        //driver will do the work of taking screenshot and then we will store that in path

        // Now here is getScreenshotAs is asking how to store ss in which format and where to store. Here we will store in file format and we store this file format in "File" variable
        File source = driver.getScreenshotAs(OutputType.FILE);
        // Now we want to copy failtest case in destination file in such way that we will identify the screenshot belong to that testcase
        String destinationFile = System.getProperty("user.dir")+"//reports"+testCaseName+".png";
        //Now we have to copy to our local system
        FileUtils.copyFile(source, new File(destinationFile));

        //Why we are returning this destination file bcz extent report will use this and attach the ss in index.html report
        return destinationFile;
    }


}
