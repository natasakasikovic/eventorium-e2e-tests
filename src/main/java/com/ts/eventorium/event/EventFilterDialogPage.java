package com.ts.eventorium.event;

import com.ts.eventorium.event.util.EventFilter;
import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Optional;
import java.util.function.Consumer;

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
        setIfNotNull(filter.name(), value -> setField(nameInput, value));
        setIfNotNull(filter.type(), value -> selectOption(eventTypeSelect, value, OPTION_PATTERN));
        setIfNotNull(filter.description(), value -> setField(descriptionInput, value));
        setIfNotNull(filter.city(), value -> selectOption(citySelect, value, OPTION_PATTERN));
        setIfNotNull(filter.maxParticipants(), value -> setField(maxParticipantsInput, value.toString()));
        setIfNotNull(filter.from(), value -> setDate(fromDateSelect, value.toString()));
        setIfNotNull(filter.to(), value -> setDate(toDateSelect, value.toString()));
    }

    private <T> void setIfNotNull(T value, Consumer<T> setter) {
        Optional.ofNullable(value).ifPresent(setter);
    }

    public void setDate(By field, String date) {
        WebElement element = waitUntil(ExpectedConditions.visibilityOfElementLocated(field));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys(date);
    }
}