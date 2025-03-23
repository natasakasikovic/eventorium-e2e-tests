package com.ts.eventorium.event;

import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;

public class BudgetPlanningPage extends PageBase {

    @FindBy(id = "submit")
    private WebElement nextButton;

    @FindBy(xpath = "((//div[@role='tablist'])[1]/div/div[@role='tab'])[1]")
    private WebElement plannerTab;

    @FindBy(xpath = "((//div[@role='tablist'])[1]/div/div[@role='tab'])[2]")
    private WebElement spentTab;

    @FindBy(xpath = "//app-budget-items")
    private WebElement budgetItems;

    @FindBy(id = "mat-select-value-15")
    private WebElement newCategorySelection;

    private final By categoryTabs = By.xpath("(//div[@role='tablist'])[2]/div/div[@role='tab']");

    public void selectCategory(String name) {
        findCategory(name).ifPresent(WebElement::click);
    }

    public void clickPlannerTab() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(plannerTab))
                .click();
    }

    public Optional<WebElement> findCategory(String name) {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(budgetItems));

        return findElements(categoryTabs).stream()
                .filter(category -> category.getText().equals(name))
                .findFirst();
    }

}
