package com.ts.eventorium.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    protected void click(By locator) {
        findElement(locator).click();
    }

    protected void waitFor(long time, TimeUnit unit) {
        driver.manage().timeouts().implicitlyWait(time, unit);
    }

}
