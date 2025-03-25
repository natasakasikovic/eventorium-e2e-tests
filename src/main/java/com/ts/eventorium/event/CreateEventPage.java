package com.ts.eventorium.event;

import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CreateEventPage extends PageBase {

    @FindBy(css = "#eventTypeSelect")
    private WebElement eventTypeSelect;

    @FindBy(xpath = "//mat-select[@formcontrolname='privacy']")
    private WebElement privacySelect;

    @FindBy(xpath = "//mat-select[@formcontrolname='city']")
    private WebElement citySelect;

    @FindBy(id = "mat-input-2")
    private WebElement nameInput;

    @FindBy(id = "mat-input-3")
    private WebElement descriptionInput;

    @FindBy(id = "mat-input-4")
    private WebElement maxParticipantsInput;

    @FindBy(id = "mat-input-5")
    private WebElement addressInput;

    @FindBy(id = "mat-input-6")
    private WebElement eventDateInput;

    @FindBy(id = "submit")
    private WebElement budgetPlanningButton;

    private final By options = By.xpath("//mat-option/span");

    public BudgetPlanningPage clickBudgetPlanningButton() {
        budgetPlanningButton.click();
        return PageFactory.initElements(driver, BudgetPlanningPage.class);
    }

    public void setName(String name) {
        nameInput.sendKeys(name);
    }

    public void setDescription(String description) {
        descriptionInput.sendKeys(description);
    }

    public void setMaxParticipants(String maxParticipants) {
        maxParticipantsInput.sendKeys(maxParticipants);
    }

    public void setAddress(String address) {
        addressInput.sendKeys(address);
    }

    public void setEventDate(String eventDate) {
        eventDateInput.sendKeys(eventDate);
    }

    public void selectEventType(String eventType) {
        selectOption(eventTypeSelect, eventType);
    }

    public void selectPrivacy(String privacy) {
        selectOption(privacySelect, privacy);
    }

    public void selectCity(String city) {
        selectOption(citySelect, city);
    }

    private void selectOption(WebElement select, String option) {
        select.click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(options));

        findElements(options).stream()
                .filter(element -> element.getText().equals(option))
                .findFirst()
                .ifPresent(element -> {
                    wait.until(ExpectedConditions.elementToBeClickable(element));
                    element.click();
                });
    }
}
