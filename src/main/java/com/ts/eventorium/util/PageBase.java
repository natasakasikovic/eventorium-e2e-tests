package com.ts.eventorium.util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public abstract class PageBase {

    protected static WebDriver driver;

    public static void setDriver(WebDriver driver) {
        PageBase.driver = driver;
    }

    protected Optional<WebElement> findElement(By locator) {
        try {
            return Optional.of(driver.findElement(locator));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    protected void set(By locator, String value) {
        findElement(locator).ifPresent(element -> {
            element.clear();
            element.sendKeys(value);
        });
    }

    protected void scrollTo(By locator) {
        findElement(locator).ifPresent(element ->
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element));
    }

    protected void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void click(By locator) {
        findElement(locator).ifPresent(WebElement::click);
    }

    protected void clickJs(By locator) {
        findElement(locator).ifPresent(element ->
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element));
    }

    protected void clickRadioButton(By locator) {
        findElement(locator).ifPresent(button -> {
            if (!button.isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            }
        });
    }

    protected void waitFor(long time, TimeUnit unit) {
        driver.manage().timeouts().implicitlyWait(time, unit);
    }

    protected void selectOption(WebElement matSelect, String option, String optionPattern) {
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.className("cdk-overlay-backdrop")));

        WebElement trigger = matSelect.findElement(By.className("mat-mdc-select-trigger"));
        waitUntil(ExpectedConditions.elementToBeClickable(trigger)).click();

        String optionXpath = String.format(optionPattern, option);
        WebElement optionElement = waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionXpath)));
        waitUntil(ExpectedConditions.elementToBeClickable(optionElement)).click();
    }

    protected <T> T waitUntil(ExpectedCondition<T> condition) {
        return waitUntil(condition, 5);
    }

    protected <T> T waitUntil(ExpectedCondition<T> condition, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        return wait.until(condition);
    }

}
