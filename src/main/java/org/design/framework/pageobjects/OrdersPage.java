package org.design.framework.pageobjects;

import org.design.framework.abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class OrdersPage extends AbstractComponent {
    private WebDriver driver;

    @FindBy(xpath = "//th[@scope='row']")
    private List<WebElement> ordersIDs;

    @FindBy(xpath = "//tr/td[2]")
    private List<WebElement> ordersNames;

    public OrdersPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<String> getAllOrdersIds() {
        return ordersIDs.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getAllOrdersNames() {
        return ordersNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }

}
