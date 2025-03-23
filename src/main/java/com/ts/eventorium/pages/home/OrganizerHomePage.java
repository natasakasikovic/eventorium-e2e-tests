package com.ts.eventorium.pages.home;

import com.ts.eventorium.pages.event.CreateEventPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrganizerHomePage extends AuthenticatedHomePage {

    @FindBy(xpath = "//button/span[text()='Create event']/..")
    private WebElement createEventButton;

    public CreateEventPage clickCreateEventButton() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(createEventButton)).click();

        return PageFactory.initElements(driver, CreateEventPage.class);
    }

}
