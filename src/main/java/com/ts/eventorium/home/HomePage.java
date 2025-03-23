package com.ts.eventorium.home;

import com.ts.eventorium.auth.LoginPage;
import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends PageBase {

    @FindBy(xpath = "//button/span[text()='Login']/..")
    private WebElement loginButton;

    @FindBy(xpath = "//button/span[text()='Signup']/..")
    private WebElement signupButton;

    public LoginPage clickLoginButton() {
        loginButton.click();
        return PageFactory.initElements(driver, LoginPage.class);
    }

}
