package org.design.framework.test;

import org.design.framework.testComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LoginValidationTest2 extends BaseTest {

    @Test
    public void loginInvalidCredentialsTest() {
//        Assert.assertTrue(false);  //todo: comment/uncomment this line to test screenshot feature
        landingPage.loginApplication("invalid@credentials.com", "invalid");
        String loginAlert = landingPage.getLoginAlert();
        Assert.assertEquals(loginAlert, "Incorrect email or password.");
    }

    @Test(enabled = false)
    public void scheduledFailureTest() {
        String title = driver.getTitle();
        System.out.println("title = " + title);
        Assert.assertEquals(title, "Deliberately incorrect title");
    }


}
