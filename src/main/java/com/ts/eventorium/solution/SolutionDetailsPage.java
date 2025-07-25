package com.ts.eventorium.solution;

import com.ts.eventorium.home.HomePage;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SolutionDetailsPage extends HomePage {

    private final By purchaseButton = By.xpath("//button[.//span[contains(text(), 'Purchase')]]");
    private final By reserveButton = By.xpath("//button[.//span[contains(text(), 'Reserve')]]");
    private final By addToPlannerButton = By.xpath("//button[.//span[contains(text(), 'Add to planner')]]");

    public void clickPurchaseForBudget() {
        waitUntil(elementToBeClickable(purchaseButton));
        scrollTo(purchaseButton);
        click(purchaseButton);
    }

    public void clickAddToPlanner() {
        waitUntil(elementToBeClickable(addToPlannerButton));
        scrollTo(addToPlannerButton);
        click(addToPlannerButton);
    }

    public void clickReserveButton() {
        waitUntil(elementToBeClickable(reserveButton));
        scrollTo(reserveButton);
        click(reserveButton);
    }

    public void makeReservation(String fromTime, String toTime) {
        ReservationDialog reservationDialog = new ReservationDialog();
        reservationDialog.reserveService(fromTime, toTime);
    }

}
