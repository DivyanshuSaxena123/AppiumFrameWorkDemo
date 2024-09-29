package org.utils.iOS;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.utils.AppiumUtils;

import java.util.HashMap;
import java.util.Map;

public class IOSGesture extends AppiumUtils {
    // Here we will list methods for Gesture which can be used many time like swipe, scroll and long press
    IOSDriver driver;

    public IOSGesture(IOSDriver driver){
        //super(driver);
        this.driver = driver;
    }



    //Long Press Action
    public void LongPressAction(WebElement ele){
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "duration",2000));
    }

    //Scroll to Text
    public void Scrolling(WebElement ele) throws InterruptedException {
        Map<String, String> scrollObject = new HashMap<>();
        scrollObject.put("direction", "down");
        scrollObject.put("element", ((RemoteWebElement)ele).getId());
        driver.executeScript("mobile:scroll", scrollObject);
        Thread.sleep(2000);

    }



}

