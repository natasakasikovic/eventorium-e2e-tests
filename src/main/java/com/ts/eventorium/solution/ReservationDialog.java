package com.ts.eventorium.solution;

import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ReservationDialog extends PageBase {

    private final By fromInput = By.cssSelector("input[formcontrolname='startingTime']");
    private final By toInput = By.cssSelector("input[formcontrolname='endingTime']");
    private final By reserveButton = By.id("reserve-button");

    public void setStartingTime(String time) {
        WebElement from = waitUntil(ExpectedConditions.visibilityOfElementLocated(fromInput));
        from.clear();
        from.sendKeys(time);
        from.sendKeys(Keys.TAB);
    }

    public void setEndingTime(String time) {
        WebElement to = waitUntil(ExpectedConditions.visibilityOfElementLocated(toInput));
        to.clear();
        to.sendKeys(time);
        to.sendKeys(Keys.TAB);
    }

    public void clickReserve() {
        waitUntil(ExpectedConditions.elementToBeClickable(reserveButton)).click();
        findElement(By.cssSelector("body")).ifPresent(element -> element.sendKeys(Keys.ESCAPE));
    }

    public void reserveService(String fromTime, String toTime) {
        setStartingTime(fromTime);
        setEndingTime(toTime);
        clickReserve();
    }
}

