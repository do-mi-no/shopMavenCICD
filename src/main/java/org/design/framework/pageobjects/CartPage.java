package org.design.framework.pageobjects;

import org.design.framework.abstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponent {
    private WebDriver driver;
    private By myCartProductsNamesBy = By.xpath("//*[@class='cartSection']/h3");

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> getProductsFromCart() {
        return driver.findElements(myCartProductsNamesBy);
    }

}
