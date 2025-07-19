package com.ts.eventorium.event;

import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


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

    private static final String OPTION_PATTERN = "//mat-option//span[contains(text(), '%s')]";

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
        selectOption(eventTypeSelect, eventType, OPTION_PATTERN);
    }

    public void selectPrivacy(String privacy) {
        selectOption(privacySelect, privacy, OPTION_PATTERN);
    }

    public void selectCity(String city) {
        selectOption(citySelect, city, OPTION_PATTERN);
    }

}
