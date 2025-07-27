package com.ts.eventorium.util;

import com.ts.eventorium.home.HomePage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class TestBase {

    private WebDriver driver;
    protected HomePage homePage;
    private static final String EVENTORIUM_URL = "http://localhost:4200/home";

    @BeforeSuite
    public void initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeClass
    public void loadApplication() {
        driver.get(EVENTORIUM_URL);
        PageBase.setDriver(driver);
        homePage = PageFactory.initElements(driver, HomePage.class);
    }

    @AfterMethod
    public void takeFailedResultScreenshot(ITestResult testResult) throws IOException {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            File screenshotsDir = new File(System.getProperty("user.dir") + "/resources/screenshots");
            if (!screenshotsDir.exists() && !screenshotsDir.mkdirs()) {
                throw new IOException("Could not screenshot file");
            }

            File destination =
                    new File(screenshotsDir,"(" + Instant.now().toEpochMilli() + ") " + testResult.getName() + ".png");
            FileHandler.copy(source, destination);
            System.out.println("Screenshot Located At " + destination);
        }
    }

    @AfterSuite
    public void quitDriver() {
        driver.quit();
    }
}
