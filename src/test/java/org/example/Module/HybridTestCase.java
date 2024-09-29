package org.example.Module;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.example.TestUtils.AndroidBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageObjects.Android.CartPage;
import org.pageObjects.Android.FormPage;
import org.pageObjects.Android.ProductCatalouge;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.utils.AppiumUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class HybridTestCase extends AndroidBaseTest {

    // The Purpose of this is that before calling any Method it will look for @BeforeMethod annotation and execute this annotation first and then run test case
    @BeforeMethod(alwaysRun = true)
    public void preSetup() throws InterruptedException {
        //By using app activity - screen to homePage
        formPage.setActivity();
    }



    @Test(dataProvider = "getData", groups = {"Smoke"})
    // When we don't use HashMap
    //public void HybridAction(String country, String name, String gender) throws InterruptedException {
    public void HybridAction(HashMap<String, String> input) throws InterruptedException {

        // Making an object of FormPage - we can object here and in base test also as we know by launching the app it will and on "Form Page"
        //FormPage formPage = new FormPage(driver);
        //formPage.selectCountry(country);
        formPage.selectCountry(input.get("country"));
        //formPage.setNameField(name);
        formPage.setNameField(input.get("name"));
        //formPage.selectGender(gender);
        formPage.selectGender(input.get("gender"));

        ProductCatalouge catalouge = formPage.submitForm();
        /*
        Now suppose if our product has 10-20 screens that means we
        have to make 10-20 object which is not feasible so, now
        what we can do is when we click on any cta that leads to next page we will
        return the object of new page
         */
        //ProductCatalouge catalouge = new ProductCatalouge(driver);
        catalouge.addItemtoCartByIndex(0);
        catalouge.addItemtoCartByIndex(0);
        CartPage cartPage = catalouge.goToCartPage();
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.attributeContains(driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/toolbar_title")),"text", "Cart"));
        AppiumUtils newFunction =  new AppiumUtils();
        newFunction.waitForElementtoAppear(driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/toolbar_title")), driver);

        Double totalSumCartItems = cartPage.getProductSum();
        Double amountShowed = cartPage.getTotalAmountDisplayed();
        System.out.println("Value of CartItem :" + totalSumCartItems);
        System.out.println("Value Displayed :" + amountShowed);

        Assert.assertEquals(totalSumCartItems, amountShowed);

        // Test case 4 - Long Press on T&C , Tapping on checkbox
        cartPage.acceptTermsConditions();



        // Test case 4 - Long Press on T&C , Tapping on checkbox
        //WebElement element = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/termsButton"));
        //((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "duration",2000));


        //String alertTitle = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/alertTitle")).getText();
        //String popupTitle = cartPage.getTitle();
        //Assert.assertEquals(popupTitle, "Terms Of Conditions");

        //driver.findElement(AppiumBy.id("android:id/button1")).click();
        cartPage.submitOrder();
        //driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
        //driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(9000);



        //--------------------------------------------------------------------------------
        /*
        Hybrid app are those app which include opening of URL
        either with in the app or outside the app. The drive we declared in Base Class
        i.e UiAutomator only understand Native app so we have to make aware of it

        Context handle will fetch context views in the app it is completely native or it
        has web view. Context view name will depend on developer what they have given
        */
        Set<String> contexts = driver.getContextHandles();
        for (String contextName : contexts ){
            System.out.println(contextName);
        }

        // there are 2 way to automate the Hybrid app - 1. By checking the context view 2. By Using Chrome Driver
        driver.context("WEBVIEW_com.androidsample.generalstore"); // set the path of chrome drive in Base Test
        Thread.sleep(30000);
        driver.findElement(By.name("q")).sendKeys("upgrad");
        Thread.sleep(3000);
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        Thread.sleep(9000);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));

        // now if u want to again perform any action on App then we have to again switch context from web to native
        driver.context("NATIVE_APP");
        Thread.sleep(3000);

    }

    //------------------------------------------------------------------------------------------------
        /*
        Now as we can see that while filling details in form we have hardcoded instead of that
        we can use TestNg annotation - Data provider, Parameterization
         */


    // Data Provider return 2-dimensional array or Hashmap it can be of any datatype like int[][] { {name1, country1, gender1 },  {name2, country2, gender2 }  };
    @DataProvider
    public Object[][] getData() throws IOException {
        // so we will return an object of 2 dimensional array
        List<HashMap<String, String>> testingData = getJsonData(System.getProperty("user.dir")+"//src//test//java//testData//eCommerce.json");
        //return new Object[][] { {"Argentina","Himanshu","Male"},{"Angola","Divya","female"} };
        return new Object[][] { {testingData.get(0)}, {testingData.get(1)} };
    }

    // Above is look like we have hardcode now we try to build a json of data and try to use json to fill data in form page

    /*
    Parse Json -> Json String By using Common Io Dependency
    Json String -> Hash Map By using Jackson Data Bind Dependency
    HashMap -> TestCase (TestNg Data Provider)
     */

}
