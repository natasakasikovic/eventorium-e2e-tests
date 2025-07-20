package com.ts.eventorium.event;

import com.ts.eventorium.home.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Optional;

public class EventDetailsPage extends HomePage {

    private final By exportGuestListButton = By.xpath("//button[.//mat-icon[text()='picture_as_pdf'] and contains(., 'Export guest list to PDF')]");

    public Optional<WebElement> getExportButton() {
        WebElement element = waitUntil(ExpectedConditions.visibilityOfElementLocated(exportGuestListButton));
        return Optional.of(element);
    }

    public void clickExportGuestList() {
        waitUntil(ExpectedConditions.elementToBeClickable(exportGuestListButton));
        click(exportGuestListButton);
        waitForPdf();
    }

    private void waitForPdf() {
        long startTime = System.currentTimeMillis();
        waitUntil(driver -> System.currentTimeMillis() - startTime >= 4000, 5);
    }

}
