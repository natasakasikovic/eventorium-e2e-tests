package com.ts.eventorium.solution;

import com.ts.eventorium.event.BudgetPlanningPage;
import com.ts.eventorium.home.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.Optional;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ProductDetailsPage extends HomePage {

    private final By purchaseButton = By.xpath("//button[.//span[contains(text(), 'Purchase')]]");
    private final By eventSelect = By.xpath("//mat-select[@formcontrolname='event']");
    private final By options = By.xpath("//mat-option/span");
    private final By plannedAmountInput = By.xpath("//input[@formcontrolname='plannedAmount']");
    private final By confirmButton = By.xpath("//button[span[text()='Confirm']]");
    private final By addToPlannerButton = By.xpath("//button[.//span[contains(text(), 'Add to planner')]]");

    public void purchaseProduct(String eventName, double plannedAmount) {
        clickPurchaseButton();
        waitUntil(visibilityOfElementLocated(eventSelect)).click();

        findOption(eventName).ifPresent(element -> {
            scrollTo(element);
            element.click();
        });
        set(plannedAmountInput, String.valueOf(plannedAmount));
        click(confirmButton);
    }

    public BudgetPlanningPage clickPurchaseForBudget() {
        clickPurchaseButton();
        return PageFactory.initElements(driver, BudgetPlanningPage.class);
    }

    private void clickPurchaseButton() {
        waitUntil(elementToBeClickable(purchaseButton), 5).click();
    }

    private Optional<WebElement> findOption(String option) {
        return findElements(options).stream()
                .filter(element -> element.getText().contains(option))
                .findFirst();
    }

    public BudgetPlanningPage clickAddToPlanner() {
        waitUntil(elementToBeClickable(addToPlannerButton)).click();
        return PageFactory.initElements(driver, BudgetPlanningPage.class);
    }

}
