package com.ts.eventorium.home;

import com.ts.eventorium.auth.LoginPage;
import com.ts.eventorium.event.EventOverviewPage;
import com.ts.eventorium.solution.ProductOverviewPage;
import com.ts.eventorium.util.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Optional;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class HomePage extends PageBase {

    @FindBy(xpath = "//button/span[text()='Login']/..")
    private WebElement loginButton;

    @FindBy(xpath = "//button/span[text()='Signup']/..")
    private WebElement signupButton;

    @FindBy(xpath = "//button/mat-icon[text()='menu']/..")
    private WebElement drawerButton;

    private final By drawer = By.xpath("//mat-sidenav-container/div[contains(@class, 'mat-drawer-shown')]");
    private final By logoutButton = By.xpath("//button/span[text()='LogOut']/..");
    private final By toaster = By.cssSelector(".toast-top-right");

    private static final String DRAWER_OPTION_PATTERN = "//mat-nav-list/mat-list-item[.//span[text()='%s']]";
    private static final String SEE_MORE_CONTENT_PATTERN = "//h1[contains(text(), '%s')]/ancestor::div[@class='cards-screen']//div[@class='see-more']/button";
    private static final String TOASTER_MESSAGE_PATTERN = "//div[contains(@class, 'toast-message') and contains(text(), '%s')]";

    public LoginPage clickLoginButton() {
        loginButton.click();
        return PageFactory.initElements(driver, LoginPage.class);
    }

    public HomePage clickLogout() {
        findElement(logoutButton).ifPresent(WebElement::click);
        return PageFactory.initElements(driver, HomePage.class);
    }

    public ProductOverviewPage clickSeeMoreProducts() {
        clickSeeMoreContentButton("product");
        return PageFactory.initElements(driver, ProductOverviewPage.class);
    }

    public void clickSeeMoreServices() {
        clickSeeMoreContentButton("service");
    }

    public EventOverviewPage clickSeeMoreEvents() {
        clickSeeMoreContentButton("event");
        return PageFactory.initElements(driver, EventOverviewPage.class);
    }

    private void clickSeeMoreContentButton(String option) {
        findElement(By.xpath(String.format(SEE_MORE_CONTENT_PATTERN, option))).ifPresent(element -> {
            scrollTo(element);
            element.click();
        });
    }

    public WebElement findToasterWithMessage(String message) {
        String xpath = String.format(TOASTER_MESSAGE_PATTERN, message);
        return waitUntil(visibilityOfElementLocated(By.xpath(xpath)));
    }

    public Optional<WebElement> findToaster() {
        return findElement(toaster);
    }

    protected <T extends HomePage> T clickHome(Class<T> pageClass) {
        findDrawerOption("Home").ifPresent(WebElement::click);
        return PageFactory.initElements(driver, pageClass);
    }

    public Optional<WebElement> findDrawerOption(String option) {
        drawerButton.click();
        waitUntil(visibilityOfElementLocated(drawer));

        return findElement(By.xpath(String.format(DRAWER_OPTION_PATTERN, option)));
    }

}
