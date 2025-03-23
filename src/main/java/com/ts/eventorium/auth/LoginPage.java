package com.ts.eventorium.auth;

import com.ts.eventorium.home.OrganizerHomePage;
import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends PageBase {

    private static final String ORGANIZER_EMAIL = "organizer@gmail.com";
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String PROVIDER_EMAIL = "provider@gmail.com";
    private static final String PASSWORD = "pera";

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button/span[text()='Sign In']/..")
    private WebElement signInButton;

    public void setEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void setPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void setEmailAndPassword(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    public OrganizerHomePage signInAsOrganizer() {
        setEmail(ORGANIZER_EMAIL);
        setPassword(PASSWORD);
        clickSignInButton();
        return PageFactory.initElements(driver, OrganizerHomePage.class);
    }

    public void signInAsProvider() {
        setEmail(PROVIDER_EMAIL);
        setPassword(PASSWORD);
        clickSignInButton();
    }

    public void signInAsAdmin() {
        setEmail(ADMIN_EMAIL);
        setPassword(PASSWORD);
        clickSignInButton();
    }

}
