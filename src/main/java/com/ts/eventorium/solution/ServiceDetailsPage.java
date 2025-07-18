package com.ts.eventorium.solution;

import com.ts.eventorium.home.HomePage;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class ServiceDetailsPage extends HomePage {

    private final By addToPlannerButton = By.xpath("//button[.//span[contains(text(), 'Add to planner')]]");

    public void clickAddToPlanner() {
        waitUntil(elementToBeClickable(addToPlannerButton)).click();
    }
}
