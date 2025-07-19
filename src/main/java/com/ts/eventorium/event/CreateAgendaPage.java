package com.ts.eventorium.event;

import com.ts.eventorium.auth.OrganizerPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CreateAgendaPage extends OrganizerPage {
    @FindBy(css = "input[formControlName='name']")
    private WebElement nameInput;

    @FindBy(css = "input[formControlName='location']")
    private WebElement locationInput;

    @FindBy(css = "textarea[formControlName='description']")
    private WebElement descriptionInput;

    @FindBy(css = "input[formControlName='from']")
    private WebElement fromInput;

    @FindBy(css = "input[formControlName='to']")
    private WebElement toInput;

    @FindBy(css = ".add-button button")
    private WebElement addActivityButton;

    @FindBy(css = "table[mat-table]")
    private WebElement agendaTable;

    @FindBy(css = ".finish-button")
    private WebElement finishButton;

    @FindBy(css = "#submit")
    private WebElement submitButton;

    public Optional<WebElement> findActivityForm() {
        waitUntil(ExpectedConditions.visibilityOf(nameInput));

        return Optional.ofNullable(nameInput);
    }

    public void fillActivity(String name, String location, String description, String from, String to) {
        nameInput.clear();
        nameInput.sendKeys(name);

        locationInput.clear();
        locationInput.sendKeys(location);

        descriptionInput.clear();
        descriptionInput.sendKeys(description);

        fromInput.clear();
        fromInput.sendKeys(from);
        fromInput.sendKeys(Keys.ENTER);

        toInput.clear();
        toInput.sendKeys(to);
        toInput.sendKeys(Keys.ENTER);
    }

    public void clickAddActivity() {
        addActivityButton.click();
    }

    public boolean isActivityAdded(String activityName) {
        return agendaTable.getText().contains(activityName);
    }

    public void finishAgenda() {
        if (finishButton.isDisplayed()) finishButton.click();
        else submitButton.click();
    }

    public void removeActivityByName(String activityName) {
        List<WebElement> rows = driver.findElements(By.cssSelector("table[mat-table] tr"));

        for (WebElement row : rows) {
            if (row.getText().contains(activityName)) {
                row.findElement(By.cssSelector("button[mat-icon-button]")).click();
                return;
            }
        }

        throw new NoSuchElementException("Activity with name '" + activityName + "' not found.");
    }

    public WebElement findDialog(String expectedMessage) {
        WebElement messageElement = waitUntil(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//mat-dialog-content[contains(@class, 'custom-dialog-content')]")
        ));
        if (messageElement.getText().contains(expectedMessage)) return messageElement;
        return null;
    }

    public void closeDialog() {
        WebElement overlay = driver.findElement(By.cssSelector(".cdk-overlay-backdrop"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", overlay);
    }
}
