package org.design.framework.test;

import org.design.framework.pageobjects.*;
import org.design.framework.testComponents.BaseTest;
import org.design.framework.testComponents.Retry;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SubmitOrderTest extends BaseTest {
    String adidas = "ADIDAS ORIGINAL";
    String zara = "ZARA COAT 3";


    @Test()
    public void addProductsToCartTest() throws InterruptedException {
        ProductsPage productsPage = landingPage.loginApplication("test@shop.com", "TestShop1");
        productsPage.addProductToCart(adidas);
        productsPage.addProductToCart(zara);

//        ------ Go to CART, check if the products have been added -----------
        CartPage cartPage = productsPage.goToCartPage();
        List<WebElement> productsInCart = cartPage.getProductsFromCart();
        boolean prod1IsInCart = productsInCart.stream().anyMatch(e -> e.getText().contains(adidas));
        Assert.assertTrue(prod1IsInCart);
        boolean prod2IsInCart = productsInCart.stream().anyMatch(e -> e.getText().contains(zara));
        Assert.assertTrue(prod2IsInCart);

//        ------ Go to checkout (enter CVV code, country) and place your order -----------
        CheckoutPage checkoutPage = cartPage.goToCheckoutPage();
        checkoutPage.sendCvvCode("9876");
        checkoutPage.selectCountry("Dominica");
        ThankYouPage thankYouPage = checkoutPage.placeOrder();

//        ------ After placing an order, make sure that: message "THANK..."?, ordered items present? -----------
        Assert.assertEquals(thankYouPage.getConfirmationMessage(), "THANKYOU FOR THE ORDER.");

        List<String> currentOrderIDs = thankYouPage.getCurrentOrderIds();
        OrdersPage ordersPage = thankYouPage.goToOrdersPage();
        List<String> globalOrderIDs = ordersPage.getAllOrdersIds();
//        printCurrentAndGlobalIDs(currentOrderIDs, globalOrderIDs);
//        every current order should be present within global orders list:
        for (String id : currentOrderIDs) {
            Assert.assertTrue(globalOrderIDs.contains(id));
        }
    }

    @Test(dependsOnMethods = "addProductsToCartTest")
    public void productsPresentInOrdersTest() throws InterruptedException {
//        Thread.sleep(1000);
        landingPage.loginApplication("test@shop.com", "TestShop1");
        OrdersPage ordersPage = landingPage.goToOrdersPage();
        List<String> allOrdersNames = ordersPage.getAllOrdersNames();
        Assert.assertTrue(allOrdersNames.contains(adidas));
        Assert.assertTrue(allOrdersNames.contains(zara));
    }

    private static void printCurrentAndGlobalIDs(List<String> currentOrderIDs, List<String> globalOrderIDs) {
        System.out.println("--- CurrentOrderIDs:");
        currentOrderIDs.forEach(System.out::println);
        System.out.println("--- GlobalOrderIDs:");
        globalOrderIDs.forEach(System.out::println);
    }
}
