package org.example.Module;


import io.appium.java_client.AppiumBy;
import org.example.TestUtils.iOSBaseTest;
import org.pageObjects.IOS.AlertViews;
import org.pageObjects.IOS.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IOSBasicsTest extends iOSBaseTest {
    @Test
    public void BasicsTest(){
        // Just like android in iOS we use these locator - xpath, classname, iOS, iOSClassChain, iOSPredictedString, accessibility , id
        //driver.findElement(AppiumBy.accessibilityId("Alert Views")).click();
        HomePage homePage = new HomePage(driver);
        homePage.selectAlertView();

        //driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == 'Text Entry'`]")).click();
        homePage.selectTextEntryButton();

        // Now, in below chain u must be wondering why we don't provide attribute and value bcz tag na
        //driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeCell")).sendKeys("Hello Divyanshu");
        homePage.enterDataInPopup("Hello Himanshu");
        //driver.findElement(AppiumBy.accessibilityId("OK")).click();
        homePage.setConfirmPopup();

        // Predicate String -  Predicate means you give the matching string here. It give flexibility to look for locator in any form
        //driver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText' AND value == 'Confirm/Cancel'")).click();
        //driver.findElement(AppiumBy.iOSNsPredicateString("type ==  'XCUIElementTypeStaticText' AND value BEGINSWITH[c] 'Confirm'")).click();
        AlertViews alertviewPage = homePage.selectAlertView();
        String getText = alertviewPage.selectiOSNativePopup();
        Assert.assertEquals(getText, "A message should be a short, complete sentence.");

        //or[c] denotes case sensitive
        //driver.findElement(AppiumBy.iOSNsPredicateString("type ==  'XCUIElementTypeStaticText' AND value ENDSWITH[c] 'Cancel'")).click();
        //String text = driver.findElement(AppiumBy.iOSNsPredicateString("type ==  'XCUIElementTypeStaticText' AND value BEGINSWITH[c] 'A message'")).getText();
        //System.out.println(text);

        driver.findElement(AppiumBy.iOSNsPredicateString("label == 'Confirm'")).click();

        //Assignment






    }
}

