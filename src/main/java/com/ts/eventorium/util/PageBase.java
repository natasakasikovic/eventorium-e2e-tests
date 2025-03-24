package com.ts.eventorium.util;

import org.openqa.selenium.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class PageBase {

    public static WebDriver driver;

    public static void setDriver(WebDriver driver) {
        PageBase.driver = driver;
    }

    protected WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    protected void set(By locator, String value) {
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected void scrollTo(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", findElement(locator));
    }

    protected void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void click(By locator) {
        findElement(locator).click();
    }

    protected void clickJs(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", findElement(locator));
    }

    protected void clickRadioButton(By locator) {
        WebElement button = findElement(locator);
        if(!button.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    protected void waitFor(long time, TimeUnit unit) {
        driver.manage().timeouts().implicitlyWait(time, unit);
    }

}
