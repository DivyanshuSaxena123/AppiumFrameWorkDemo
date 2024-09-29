package org.pageObjects.Android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.utils.Android.AndroidGesture;
import org.utils.AppiumUtils;

import javax.xml.xpath.XPath;
import java.util.List;

public class CartPage extends AndroidGesture {
    AndroidDriver driver;

    public CartPage(AndroidDriver driver)
    {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    private List<WebElement> productList;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
    private WebElement totalAmount;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
    private WebElement terms;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement alertCloseButton;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/alertTitle")
    private WebElement alertTitle;

    @AndroidFindBy(className = "android.widget.CheckBox")
    private WebElement checkbox;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
    private WebElement proceed;


    public Double getProductSum(){
        int count = productList.size();
        double totalsum = 0;
        for (int i=0; i < count; i++) {
            String amountString = productList.get(i).getAttribute("text");
            System.out.println(amountString);
            // now we are getting price as a string we can't perform addition on string so, we have to remove dollar from amount $160.93 $is at index 0

            Double price = getUpdatedFormattedAmount(amountString);
            totalsum = totalsum + price; //160.97 + 120 =280.97
        }
        return totalsum;
    }

    public Double getTotalAmountDisplayed(){
     return getUpdatedFormattedAmount(totalAmount.getText());
    }

    public Double getUpdatedFormattedAmount(String amount) {
        //Double price = Double.parseDouble(amount.substring(1));
        Double price = getFormattedAmount(amount);
        return price;
    }

    public void acceptTermsConditions(){
        LongPressAction(terms);
        String popupTitle = getTitle();
        Assert.assertEquals(popupTitle, "Terms Of Conditions");
        alertCloseButton.click();
    }

    public String getTitle(){
        //System.out.println(alertTitle.getText());
        String title =  alertTitle.getText();
        return title;

    }
    public void submitOrder(){
        checkbox.click();
        proceed.click();
    }




}
