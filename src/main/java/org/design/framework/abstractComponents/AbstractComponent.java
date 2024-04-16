package org.design.framework.abstractComponents;

import org.design.framework.pageobjects.CartPage;
import org.design.framework.pageobjects.CheckoutPage;
import org.design.framework.pageobjects.OrdersPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractComponent {
    WebDriver driver;
    @FindBy(xpath = "//button[@routerlink='/dashboard/myorders']")
    WebElement ordersButton;
    @FindBy(xpath = "//button[contains(text(),'Checkout')]")
    WebElement checkoutButton;
    @FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
    WebElement cartButton;

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected AbstractComponent() {
    }

    public void waitForElementToAppear(By productsBy) {
        waitForElementToAppear(driver.findElement(productsBy));
    }

    public void waitForElementToAppear(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitForElementToDisappear(WebElement webElement) throws InterruptedException {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.invisibilityOf(webElement));
        //There is an issue with the spinner (there is another 'invisible' spinner that causes not expected delay...; 1:05):
        //https://www.udemy.com/course/selenium-real-time-examplesinterview-questions/learn/lecture/33476192#overview
        Thread.sleep(1500); //todo: 1000ms usually is ok (330ms is random; change it if you want to test Retry.class)
    }

    private void waitForPageToFullyLoad(int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

//    public LandingPage goToLandingPage() {
//        driver.get("https://www.rahulshettyacademy.com/client");
//        return new LandingPage(driver);

//    }

    public CartPage goToCartPage() {
        cartButton.click();
        waitForPageToFullyLoad(3);
        return new CartPage(driver);
    }

    public CheckoutPage goToCheckoutPage() {
        checkoutButton.click();
        return new CheckoutPage(driver);
    }

    public OrdersPage goToOrdersPage() {
        ordersButton.click();
        return new OrdersPage(driver);
    }
}
