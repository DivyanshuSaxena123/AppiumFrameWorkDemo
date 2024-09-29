package org.utils.Android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.utils.AppiumUtils;

public class AndroidGesture extends AppiumUtils {
    // Here we will list methods for Gesture which can be used many time like swipe, scroll and long press
    AndroidDriver driver;

    public AndroidGesture(AndroidDriver driver){
        //super(driver);
        this.driver = driver;
    }
    public void scrollToText(String countryName){
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+countryName+"\"));")).click();
    }


    //Long Press Action
    public void LongPressAction(WebElement ele){
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "duration",2000));
    }



}
