package org.design.framework.test;

import org.design.framework.testComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LoginValidationTest1 extends BaseTest {

    @Test(dataProvider = "getLoginAndPassword")
    public void loginValidCredentialsTest(HashMap<String, String> dataMap) {
        landingPage.loginApplication(dataMap.get("email"), dataMap.get("password"));
        String loginAlert = landingPage.getLoginAlert();
        Assert.assertEquals(loginAlert, "Login Successfully");
    }

    @DataProvider
    public java.util.Iterator<HashMap<String, String>> getLoginAndPassword() throws IOException {
        List<HashMap<String, String>> data = getJsonDataAsHashmap(System.getProperty("user.dir") +
                "/src/test/java/org/design/framework/data/ValidLoginData.json");
        return data.iterator();
    }

}
