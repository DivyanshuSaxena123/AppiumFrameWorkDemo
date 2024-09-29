package org.pageObjects.IOS;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.utils.iOS.IOSGesture;

public class HomePage extends IOSGesture {
    IOSDriver driver;
    public HomePage(IOSDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    //driver.findElement(AppiumBy.accessibilityId("Alert Views")).click();
    @iOSXCUITFindBy(accessibility = "Alert Views")
    private WebElement alertViews;


    //driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == 'Text Entry'`]")).click();
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`label == 'Text Entry'`]")
    private WebElement textEntryMenu;

    //driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeCell")).sendKeys("Hello Divyanshu");
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell")
    private WebElement enterTextInPopup;

    //driver.findElement(AppiumBy.accessibilityId("OK")).click();
    @iOSXCUITFindBy(id = "OK")
    private  WebElement confirmPopup;

    public AlertViews selectAlertView(){
        alertViews.click();
        return new AlertViews(driver);
    }

    public void selectTextEntryButton(){
        textEntryMenu.click();
    }

    public void enterDataInPopup(String name){
        enterTextInPopup.sendKeys(name);
    }

    public void setConfirmPopup(){
        confirmPopup.click();
    }




}
