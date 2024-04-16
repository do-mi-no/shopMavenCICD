package org.design.framework.pageobjects;

import org.design.framework.abstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductsPage extends AbstractComponent {
    private WebDriver driver;
    @FindBy(css = ".ng-animating")
    private WebElement spinner;
    private By productsBy = By.cssSelector("div.mb-3");
    private By addToCart = By.cssSelector("button:last-of-type");
    private By toastMessage = By.cssSelector("#toast-container");

    public ProductsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> getProductList() {
        waitForElementToAppear(productsBy);
        return driver.findElements(productsBy);
    }

    public WebElement getProductByName(String productName) {
        return getProductList().stream()
                .filter(e -> e.findElement(By.cssSelector("b")).getText().contains(productName))
                .findFirst().orElseThrow(()->new NoSuchElementException("There is no productName: " + productName));
    }

    public void addProductToCart(String productName) throws InterruptedException {
        WebElement prod = getProductByName(productName);
        prod.findElement(addToCart).click();
        waitForElementToDisappear(spinner);
        waitForElementToAppear(toastMessage);
    }

    public void addProductWEToCart(WebElement productWE) throws InterruptedException {
//        Instant start = Instant.now();
        productWE.findElement(addToCart).click();
//        System.out.println("Duration 1 = " + Duration.between(start, Instant.now()).toMillis());
        waitForElementToDisappear(spinner);
//        System.out.println("Duration 2 = " + Duration.between(start, Instant.now()).toMillis());
        waitForElementToAppear(toastMessage);
//        System.out.println("Duration 3 = " + Duration.between(start, Instant.now()).toMillis());
    }

}
