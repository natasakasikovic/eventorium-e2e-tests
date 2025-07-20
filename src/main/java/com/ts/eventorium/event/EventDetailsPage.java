package com.ts.eventorium.event;

import com.ts.eventorium.home.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EventDetailsPage extends HomePage {

    private final By exportGuestListButton = By.xpath("//button[.//mat-icon[text()='picture_as_pdf'] and contains(., 'Export guest list to PDF')]");

    public void clickExportGuestList() {
        waitUntil(ExpectedConditions.elementToBeClickable(exportGuestListButton));
        click(exportGuestListButton);
    }

    public void waitForPdf() {
        long startTime = System.currentTimeMillis();
        waitUntil(driver -> System.currentTimeMillis() - startTime >= 2000, 3);
    }

}
