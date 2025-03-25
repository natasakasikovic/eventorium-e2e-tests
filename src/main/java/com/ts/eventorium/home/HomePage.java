package com.ts.eventorium.home;

import com.ts.eventorium.auth.LoginPage;
import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;

public class HomePage extends PageBase {

    @FindBy(xpath = "//button/span[text()='Login']/..")
    private WebElement loginButton;

    @FindBy(xpath = "//button/span[text()='Signup']/..")
    private WebElement signupButton;

    @FindBy(xpath = "//button/mat-icon[text()='menu']/..")
    private WebElement drawerButton;

    private final By drawer = By.xpath("//mat-sidenav-container/div[contains(@class, 'mat-drawer-shown')]");
    private final By logoutButton = By.xpath("//button/span[text()='LogOut']/..");

    private static final String DRAWER_OPTION_PATTERN = "//mat-nav-list/mat-list-item[.//span[text()='%s']]";

    public LoginPage clickLoginButton() {
        loginButton.click();
        return PageFactory.initElements(driver, LoginPage.class);
    }

    public HomePage logout() {
        findElement(logoutButton).ifPresent(WebElement::click);
        return PageFactory.initElements(driver, HomePage.class);
    }

    public Optional<WebElement> findDrawerOption(String option) {
        drawerButton.click();
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.visibilityOfElementLocated(drawer));

        return findElement(By.xpath(String.format(DRAWER_OPTION_PATTERN, option)));
    }

}
