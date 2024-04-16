package org.design.framework.pageobjects;

import org.design.framework.abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ThankYouPage extends AbstractComponent {
    private WebDriver driver;
    @FindBy(xpath = "//h1")
    private WebElement confirmationHeading;
    @FindBy(xpath = "//label[@class='ng-star-inserted']")
    private List<WebElement> currentOrderIDsElements;

    public ThankYouPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getConfirmationMessage() {
        return confirmationHeading.getText();
    }

    public List<String> getCurrentOrderIds() {
        return currentOrderIDsElements.stream().map(we -> we.getText()
                .replaceAll("\\W", "")).collect(Collectors.toList());
    }
}
