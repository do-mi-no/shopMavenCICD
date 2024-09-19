package org.design.framework.testComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.design.framework.pageobjects.LandingPage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;
    public static int pngCount = 0;

    public WebDriver initializeDriver() throws IOException {
        Properties prop = new Properties();
        String pathFromContentRoot = "/src/main/java/org/design/framework/resources/GlobalData.properties";
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + pathFromContentRoot);
        prop.load(fis);

        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");

        if (browserName.toLowerCase().contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--ignore-certificate-errors");
            WebDriverManager.chromedriver().setup();
            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440, 900));     //it's important to maximize window, to avoid errors (e.g. buttons not visible)
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Browser type '" + browserName + "' is not supported");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    @BeforeMethod
    public LandingPage launchApplication() throws IOException {
        initializeDriver();
        driver.get("https://www.rahulshettyacademy.com/client");
        return landingPage = new LandingPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }

    public List<HashMap<String, String>> getJsonDataAsHashmap(String filePath) throws IOException {
        //json to string
        String jsonAsString = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        FileInputStream fis = new FileInputStream(new File(filePath));

        //String to HashMap
        ObjectMapper mapper = new ObjectMapper();
        //Create HashMaps with key-value pairs (included in one List of HashMaps):
//        return mapper.readValue(jsonAsString, new TypeReference<List<HashMap<String, String>>>() {
        return mapper.readValue(fis, new TypeReference<List<HashMap<String, String>>>() {
        });
    }

    public String getScreenshot(String testCaseName, WebDriver drv) {
        TakesScreenshot ts = (TakesScreenshot) drv;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String reportsPath = System.getProperty("user.dir") + "//reports//" + pngCount++ + "_" + testCaseName + ".png";
        File file = new File(reportsPath);
        try {
            FileUtils.copyFile(source, file);
        } catch (IOException e) {
            System.err.println("Problems while copying file: " + file);
        }
        return reportsPath;
    }
}
