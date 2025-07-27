package com.ts.eventorium.event;

import com.ts.eventorium.auth.OrganizerPage;
import com.ts.eventorium.home.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        // The newly added activity always appears as the last row in the table
        WebElement lastRow = waitUntil(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table[@mat-table]//tr[td][last()]")
        ));
        WebElement firstCell = lastRow.findElement(By.xpath("td[1]"));
        return firstCell.getText().equals(activityName);
    }

    public HomePage finishAgenda() {
        if (finishButton.isDisplayed()) finishButton.click();
        else submitButton.click();
        return PageFactory.initElements(driver, HomePage.class);
    }

    public void removeActivityByName(String activityName) {
        String xpath = "//table[@mat-table]//tr[.//td[contains(text(), '" + activityName + "')]]//button[@mat-icon-button]";

        WebElement button = waitUntil(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        button.click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//table[@mat-table]//tr[.//td[contains(text(), '" + activityName + "')]]")));
    }
}
