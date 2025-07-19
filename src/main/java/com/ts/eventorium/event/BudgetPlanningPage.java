package com.ts.eventorium.event;

import com.ts.eventorium.auth.OrganizerPage;
import com.ts.eventorium.solution.ProductDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class BudgetPlanningPage extends OrganizerPage {

    @FindBy(id = "submit")
    private WebElement nextButton;

    @FindBy(xpath = "((//div[@role='tablist'])[1]/div/div[@role='tab'])[1]")
    private WebElement plannerTab;

    @FindBy(xpath = "((//div[@role='tablist'])[1]/div/div[@role='tab'])[2]")
    private WebElement purchasedAndReservedTab;

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
    private final By searchButton = By.xpath("//button[span[text()='Search']]");
    private final By suggestions = By.xpath("//div[@class='cards']/app-budget-suggestion-card");

    private final By table = By.xpath("//table");

    private static final String SEE_MORE_PATTERN = "//app-budget-suggestion-card[.//mat-card-title[text()='%s']]//button[.//span[text()='See more']]";
    private static final String ITEM_STATUS = "//tr[td[contains(., '%s')]]//mat-chip//span[contains(@class, 'mdc-evolution-chip__text-label')]";
    private static final String CARD_NAME_PATTERN = "//mat-card-title[text()='%s']";
    private static final String TABLE_NAME_PATTERN = "(//tbody/tr/td[1])[text()='%s']";
    private static final String TABLE_CATEGORY_PATTERN = "(//tbody/tr/td[2])[text()='%s']";
    private static final String TABLE_SPENT_AMOUNT = "(//tbody/tr/td[3])[text()='%s']";

    public void selectCategory(String name) {
        findTabCategory(name).ifPresent(element -> {
            element.click();
            (new WebDriverWait(driver, 5))
                    .until(elementToBeClickable(By.id(name + "-plannedInput")));
        });
    }

    public void removeCategory(String name) {
        findTabCategory(name).ifPresent(element -> {
            element.click();
            waitUntil(elementToBeClickable(deleteButton)).click();
        });
    }

    public void addCategory(String name) {
        if(findTabCategory(name).isEmpty()) {
            categorySelect.click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(categoryPanel));

            findNewCategory(name).ifPresent(element -> {
                scrollTo(element);
                element.click();
                addCategoryButton.click();
            });
        }
    }

    public List<WebElement> search(String categoryName, double plannedAmount) {
        addCategory(categoryName);
        selectCategory(categoryName);

        setPlannedAmount(categoryName, String.valueOf(plannedAmount));
        clickSearchButton();

        return findSuggestions();
    }

    public void clickPlannerTab() {
        waitUntil(elementToBeClickable(plannerTab)).click();
    }

    public void clickPurchasedAndReservedTab() {
        waitUntil(elementToBeClickable(purchasedAndReservedTab)).click();
    }

    public void clickSearchButton() {
        waitUntil(elementToBeClickable(searchButton));
        click(searchButton);
    }

    // TODO: Also allow ServiceDetailsPage
    public ProductDetailsPage clickSeeMoreButton(String solutionName) {
        waitUntil(elementToBeClickable(By.xpath(String.format(SEE_MORE_PATTERN, solutionName)))).click();
        return PageFactory.initElements(driver, ProductDetailsPage.class);
    }

    public void setPlannedAmount(String categoryName, String plannedAmount)  {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        By plannedAmountInput = By.id(categoryName + "-plannedInput");
        WebElement inputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(plannedAmountInput));

        wait.until(elementToBeClickable(inputElement));
        set(plannedAmountInput, plannedAmount);
    }

    public Optional<WebElement> findTabCategory(String name) {
        waitUntil(ExpectedConditions.visibilityOf(budgetItems));

        return findElements(categoryTabs).stream()
                .filter(category -> category.getText().equals(name))
                .findFirst();
    }

    public Optional<WebElement> findNameInTable(String name) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(table));
        return findElement(By.xpath(String.format(TABLE_NAME_PATTERN, name)));
    }

    public String getItemStatus(String itemName) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(table));
        Optional<WebElement> statusElementOpt = findElement(By.xpath(String.format(ITEM_STATUS, itemName)));

        return statusElementOpt
                .map(WebElement::getText)
                .orElse("Status not found");
    }

    private Optional<WebElement> findNewCategory(String name) {
        return findElements(options).stream()
                .filter(option -> option.getText().equals(name))
                .findFirst();
    }

    public Optional<WebElement> findByCardName(String name) {
        return findElement(By.xpath(String.format(CARD_NAME_PATTERN, name)));
    }

    private List<WebElement> findSuggestions() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(suggestions));
        return findElements(suggestions);
    }
}
