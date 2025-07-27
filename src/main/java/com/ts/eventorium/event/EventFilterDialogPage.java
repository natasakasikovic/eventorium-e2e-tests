package com.ts.eventorium.event;

import com.ts.eventorium.event.util.EventFilter;
import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EventFilterDialogPage extends PageBase {

    private final By nameInput = By.xpath("//input[@formcontrolname='name']");

    private final By eventTypeSelect = By.xpath("//mat-select[@formcontrolname='eventType']");

    private final By descriptionInput = By.xpath("//input[@formcontrolname='description']");

    private final By maxParticipantsInput = By.xpath("//input[@formcontrolname='maxParticipants']");

    private final By citySelect = By.xpath("//mat-select[@formcontrolname='city']");

    private final By fromDateSelect = By.xpath("//input[@formcontrolname='from']");

    private final By toDateSelect = By.xpath("//input[@formcontrolname='to']");

    private final By applyFiltersButton = By.xpath("//mat-dialog-actions/button");

    private static final String OPTION_PATTERN = "//mat-option//span[contains(text(), '%s')]";

    public void filter(EventFilter filter) {
        setFields(filter);

        WebElement element = waitUntil(ExpectedConditions.visibilityOfElementLocated(applyFiltersButton));
        element.click();
    }

    public void setField(By field, String value) {
        WebElement element = waitUntil(ExpectedConditions.visibilityOfElementLocated(field));
        element.clear();
        element.sendKeys(value);
    }

    private void setFields(EventFilter filter) {
        setField(nameInput, filter.name());
        selectOption(eventTypeSelect, filter.type(), OPTION_PATTERN);
        setField(descriptionInput, filter.description());
        selectOption(citySelect, filter.city(),OPTION_PATTERN);
        setField(maxParticipantsInput, filter.maxParticipants().toString());
        setDate(fromDateSelect, filter.from().toString());
        setDate(toDateSelect, filter.to().toString());
    }

    public void setDate(By field, String date) {
        WebElement element = waitUntil(ExpectedConditions.visibilityOfElementLocated(field));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys(date);
    }
}