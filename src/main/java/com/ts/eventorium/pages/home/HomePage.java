package com.ts.eventorium.pages.home;

import com.ts.eventorium.pages.auth.LoginPage;
import com.ts.eventorium.util.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    @FindBy(xpath = "//button/span[text()='Login']/..")
    private WebElement loginButton;

    @FindBy(xpath = "//button/span[text()='Signup']/..")
    private WebElement signupButton;

    public LoginPage clickLoginButton() {
        loginButton.click();
        return PageFactory.initElements(driver, LoginPage.class);
    }

}
