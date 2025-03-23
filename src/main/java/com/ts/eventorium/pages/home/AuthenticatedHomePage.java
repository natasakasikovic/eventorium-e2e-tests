package com.ts.eventorium.pages.home;

import com.ts.eventorium.util.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class AuthenticatedHomePage extends BasePage {

    @FindBy(xpath = "//button/span[text()='LogOut']/..")
    private WebElement logoutButton;

    @FindBy(xpath = "//button/mat-icon[text()='menu']/..")
    private WebElement drawerButton;

    public HomePage logout() {
        logoutButton.click();
        return PageFactory.initElements(driver, HomePage.class);
    }

}
