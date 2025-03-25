package com.ts.eventorium.event;

import com.ts.eventorium.auth.OrganizerPage;
import com.ts.eventorium.solution.ProductDetailsPage;
import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class BudgetPlanningPage extends OrganizerPage {

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
    private final By productRadio = By.xpath("//input[@value='product']");
    private final By serviceRadio = By.xpath("//input[@value='service']");
    private final By searchButton = By.xpath("//button[span[text()='Search']]");
    private final By productCards = By.xpath("//div[@class='cards']/app-product-card");
    private final By serviceCards = By.xpath("//div[@class='cards']/app-service-card");

    private static final String PRODUCT_SEE_MORE_PATTERN = "//app-product-card[.//mat-card-title[text()='%s']]//button[.//span[text()='See more']]";
    private static final String CARD_NAME_PATTERN = "//mat-card-title[text()='%s']";

    public void selectCategory(String name) {
        findTabCategory(name).ifPresent(element -> {
            element.click();
            (new WebDriverWait(driver, 5))
                    .until(ExpectedConditions.elementToBeClickable(By.id(name + "-plannedInput")));
        });
    }

    public void removeCategory(String name) {
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
                    .until(ExpectedConditions.visibilityOfElementLocated(categoryPanel));

            findNewCategory(name).ifPresent(element -> {
                scrollTo(element);
                element.click();
                addCategoryButton.click();
            });
        }
    }

    public List<WebElement> searchServices(String categoryName, double plannedAmount) {
        addCategory(categoryName);
        selectCategory(categoryName);

        clickRadioButton(serviceRadio);
        setPlannedAmount(categoryName, String.valueOf(plannedAmount));
        clickSearchButton();

        return findServices();
    }

    public List<WebElement> searchProducts(String categoryName, double plannedAmount) {
        addCategory(categoryName);
        selectCategory(categoryName);

        clickRadioButton(productRadio);
        setPlannedAmount(categoryName, String.valueOf(plannedAmount));
        clickSearchButton();

        return findProducts();
    }

    public void clickPlannerTab() {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(plannerTab))
                .click();
    }

    public void clickSearchButton() {
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(searchButton));
        clickJs(searchButton);
    }

    public ProductDetailsPage clickProductSeeMoreButton(String productName) {
        String xpath = String.format(PRODUCT_SEE_MORE_PATTERN, productName);
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)))
                .click();
        return PageFactory.initElements(driver, ProductDetailsPage.class);
    }

    public void setPlannedAmount(String categoryName, String plannedAmount)  {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        By plannedAmountInput = By.id(categoryName + "-plannedInput");
        WebElement inputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(plannedAmountInput));

        wait.until(ExpectedConditions.elementToBeClickable(inputElement));
        set(plannedAmountInput, plannedAmount);
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

    public List<WebElement> findServices() {
        waitFor(3, TimeUnit.SECONDS);
        return findElements(serviceCards);
    }

    public List<WebElement> findProducts() {
        waitFor(3, TimeUnit.SECONDS);
        return findElements(productCards);
    }

    public Optional<WebElement> findByCardName(String name) {
        return findElement(By.xpath(String.format(CARD_NAME_PATTERN, name)));
    }
}
