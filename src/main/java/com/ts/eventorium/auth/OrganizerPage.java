package com.ts.eventorium.auth;

import com.ts.eventorium.event.CreateEventPage;
import com.ts.eventorium.home.HomePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrganizerPage extends HomePage {

    @FindBy(xpath = "//button/span[text()='Create event']/..")
    private WebElement createEventButton;

    public CreateEventPage clickCreateEventButton() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(createEventButton)).click();

        return PageFactory.initElements(driver, CreateEventPage.class);
    }

}
