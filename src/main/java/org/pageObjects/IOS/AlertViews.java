package org.pageObjects.IOS;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.utils.iOS.IOSGesture;

public class AlertViews extends IOSGesture {
    IOSDriver driver;
    public AlertViews(IOSDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);

    }

    //driver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND value == 'Confirm/Cancel'")).click();
    //driver.findElement(AppiumBy.iOSNsPredicateString("type ==  'XCUIElementTypeStaticText' AND value BEGINSWITH[c] 'Confirm'")).click();
    @iOSXCUITFindBy(iOSNsPredicate = "type ==  'XCUIElementTypeStaticText' AND value BEGINSWITH[c] 'Confirm'")
    private WebElement iOSConfirmPopup;

    //String text = driver.findElement(AppiumBy.iOSNsPredicateString("type ==  'XCUIElementTypeStaticText' AND value BEGINSWITH[c] 'A message'")).getText();
    //System.out.println(text);
    @iOSXCUITFindBy(iOSNsPredicate = "type ==  'XCUIElementTypeStaticText' AND value BEGINSWITH[c] 'A message'")
    private WebElement nativePopupHeadingText;

    public String selectiOSNativePopup(){
        iOSConfirmPopup.click();
        return nativePopupHeadingText.getText();
    }
}
