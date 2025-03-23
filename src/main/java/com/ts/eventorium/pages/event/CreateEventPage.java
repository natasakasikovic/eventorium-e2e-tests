package com.ts.eventorium.pages.event;

import com.ts.eventorium.util.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateEventPage extends BasePage {

    @FindBy(css = "#submit")
    private WebElement budgetPlanningButton;

}
