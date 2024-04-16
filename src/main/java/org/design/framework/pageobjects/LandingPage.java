package org.design.framework.pageobjects;

import org.design.framework.abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class LandingPage extends AbstractComponent {
    private WebDriver driver;
    @FindBy(id = "userEmail")
    private WebElement userEmail;
    @FindBy(id = "userPassword")
    private WebElement userPassword;
    @FindBy(id = "login")
    private WebElement loginButton;
    @FindBy(css = ".ng-trigger-flyInOut")
    private WebElement loginAlert;

    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ProductsPage loginApplication(String email, String pass) {
        userEmail.sendKeys(email);
        userPassword.sendKeys(pass);
        loginButton.click();
        return new ProductsPage(driver);
    }

    public String getLoginAlert() {
        this.waitForElementToAppear(loginAlert);
        return loginAlert.getText();
    }

}
