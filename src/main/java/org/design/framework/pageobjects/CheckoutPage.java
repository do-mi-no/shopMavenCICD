package org.design.framework.pageobjects;

import org.design.framework.abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage extends AbstractComponent {
    private WebDriver driver;
    @FindBy(xpath = "//div[contains(text(),'CVV Code')]/following-sibling::input")
    private WebElement cvvTextInput;
    @FindBy(xpath = "//input[@placeholder='Select Country']")
    private WebElement countryTextInput;
    @FindBy(xpath = "//button[contains(@class,'ta-item')]")
    private List<WebElement> autocompleteOptions;
    @FindBy(xpath = "//a[contains(@class,'action__submit')]")
    private WebElement placeOrderButton;

    public CheckoutPage(WebDriver driver) {
//        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void sendCvvCode(String cvvCode) {
        cvvTextInput.sendKeys(cvvCode);
    }

    public void selectCountry(String countryName) {
        countryTextInput.sendKeys(countryName);
        autocompleteOptions.stream().filter(we -> we.getText().contains(countryName))
                .findFirst().orElseThrow().click();
    }

    public ThankYouPage placeOrder() {
        placeOrderButton.click();
        return new ThankYouPage(driver);
    }

}
