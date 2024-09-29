package org.pageObjects.Android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.utils.Android.AndroidGesture;

public class FormPage extends AndroidGesture {
    // driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
    // This annotation will help if we want to run automation in Android , iOS or Web Browser using selenium

    /*
    Now this AndroidFindBy will help in creating the findElement statement in the backend but as we can see the
    above statement to make any locator statement we have to provide driver

    So, to provide driver we have to make a constructor
     */


    // Here we wll list all locators and action to perform on them
    AndroidDriver driver;

    public FormPage(AndroidDriver driver)
    {
        super(driver);
       this.driver = driver;
       PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    @AndroidFindBy(id="com.androidsample.generalstore:id/spinnerCountry")
    private WebElement countrySelection;

    @AndroidFindBy(id="com.androidsample.generalstore:id/nameField")
    private  WebElement nameField;



    @AndroidFindBy(id="com.androidsample.generalstore:id/radioFemale")
    private  WebElement femaleOption;

    @AndroidFindBy(id="com.androidsample.generalstore:id/radioMale")
    private WebElement maleOption;

    @AndroidFindBy(id="com.androidsample.generalstore:id/btnLetsShop")
    private WebElement shop;


    //---------------------------Action Items----------------------------------

    public void selectCountry(String countryName){
        countrySelection.click();
        scrollToText(countryName);
    }


    public void setNameField(String name){
        nameField.sendKeys(name);
    }

    public void selectGender(String gender){
        if(gender.contains("Female")){
            femaleOption.click();
        }else{
            maleOption.click();
        }
    }

    public ProductCatalouge submitForm(){
        shop.click();
        return new ProductCatalouge(driver);

    }

    public void setActivity() throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("mobile: startActivity", ImmutableMap.of("intent", "com.androidsample.generalstore/com.androidsample.generalstore.MainActivity" ));
        Thread.sleep(8000);
    }

}
