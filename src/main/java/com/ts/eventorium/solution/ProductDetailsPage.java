package com.ts.eventorium.solution;

import com.ts.eventorium.event.BudgetPlanningPage;
import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailsPage extends PageBase {

    @FindBy(xpath = "//button[.//span[text()='Purchase']]")
    private WebElement purchaseButton;

    public BudgetPlanningPage clickPurchaseButton() {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(purchaseButton))
                .click();

        return PageFactory.initElements(driver, BudgetPlanningPage.class);
    }

}
