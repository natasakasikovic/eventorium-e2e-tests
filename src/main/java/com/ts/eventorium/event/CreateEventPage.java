package com.ts.eventorium.event;

import com.ts.eventorium.auth.OrganizerPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateEventPage extends OrganizerPage {

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
        eventDateInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        eventDateInput.sendKeys(Keys.DELETE);
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

    public WebElement findDialog(String expectedMessage) {
        WebElement messageElement = waitUntil(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//mat-dialog-content[contains(@class, 'custom-dialog-content')]//p[contains(text(), '" + expectedMessage + "')]")
        ));
        if (messageElement.getText().contains(expectedMessage)) return messageElement;
        return null;
    }

    public void closeDialog() {
        WebElement overlay = driver.findElement(By.cssSelector(".cdk-overlay-backdrop"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", overlay);
    }
}
