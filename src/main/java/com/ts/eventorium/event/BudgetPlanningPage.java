package com.ts.eventorium.event;

import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class BudgetPlanningPage extends PageBase {

    @FindBy(id = "submit")
    private WebElement nextButton;

    @FindBy(xpath = "((//div[@role='tablist'])[1]/div/div[@role='tab'])[1]")
    private WebElement plannerTab;

    @FindBy(xpath = "((//div[@role='tablist'])[1]/div/div[@role='tab'])[2]")
    private WebElement spentTab;

    @FindBy(xpath = "//app-budget-items")
    private WebElement budgetItems;

    @FindBy(xpath = "//mat-select")
    private WebElement categorySelect;

    @FindBy(id = "mat-select-value-15")
    private WebElement newCategorySelection;

    @FindBy(xpath = "//mat-icon[text() = 'add-circle']/..")
    private WebElement addCategoryButton;

    private final By categoryTabs = By.xpath("(//div[@role='tablist'])[2]/div/div[@role='tab']");
    private final By categoryPanel = By.xpath("//div[@role='listbox']/..");
    private final By options = By.xpath("//mat-option/span");
    private final By deleteButton = By.xpath("//mat-icon[text()='delete']/..");

    public void selectCategory(String name) {
        findTabCategory(name).ifPresent(WebElement::click);
    }

    public void clickPlannerTab() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(plannerTab))
                .click();
    }

    public void deleteCategory(String name) {
        findTabCategory(name).ifPresent(element -> {
            element.click();
            (new WebDriverWait(driver, 5))
                    .until(ExpectedConditions.elementToBeClickable(deleteButton))
                    .click();
        });
    }

    public void addCategory(String name) {
        if(findTabCategory(name).isEmpty()) {
            categorySelect.click();
            (new WebDriverWait(driver, 5))
                    .until(ExpectedConditions.visibilityOf(findElement(categoryPanel)));

            findNewCategory(name).ifPresent(element -> {
                element.click();
                addCategoryButton.click();
            });
        }
    }

    public Optional<WebElement> findTabCategory(String name) {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.visibilityOf(budgetItems));

        return findElements(categoryTabs).stream()
                .filter(category -> category.getText().equals(name))
                .findFirst();
    }

    private Optional<WebElement> findNewCategory(String name) {
        return findElements(options).stream()
                .filter(option -> option.getText().equals(name))
                .findFirst();
    }

}
