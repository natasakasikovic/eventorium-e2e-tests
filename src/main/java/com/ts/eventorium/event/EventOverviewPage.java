package com.ts.eventorium.event;

import com.ts.eventorium.home.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Optional;

public class EventOverviewPage extends HomePage {
    @FindBy(xpath = "//app-search-bar//input")
    private WebElement searchBox;

    private static final String CARD_NAME_PATTERN = "//app-event-card[.//mat-card-title[text()='%s']]";

    public Optional<WebElement> findCard(String eventName) {
        String xpath = String.format(CARD_NAME_PATTERN, eventName);
        try {
            WebElement element = waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            return Optional.of(element);
        } catch (TimeoutException e) {
            return Optional.empty();
        }
    }

    public void search(String eventName) {
        searchBox.sendKeys(eventName);
        searchBox.sendKeys(Keys.ENTER);
    }
}
