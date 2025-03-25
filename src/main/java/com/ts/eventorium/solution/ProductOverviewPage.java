package com.ts.eventorium.solution;

import com.ts.eventorium.home.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;

public class ProductOverviewPage extends HomePage {

    @FindBy(xpath = "//app-search-bar//input")
    private WebElement searchBox;

    @FindBy(xpath = "//mat-icon[text()='tune']/..")
    private WebElement filterButton;

    private final By productCards = By.xpath("//app-product-card");

    private static final String CARD_NAME_PATTERN = "//app-product-card[.//mat-card-title[text()='%s']]";
    private static final String SEE_MORE_BUTTON_PATTERN = "//app-product-card[.//mat-card-title[text()='%s']]//button";

    public ProductDetailsPage clickSeeMoreButton(String productName) {
        WebElement button = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions
                        .elementToBeClickable(By.xpath(String.format(SEE_MORE_BUTTON_PATTERN, productName))));
        scrollTo(button);
        button.click();
        return PageFactory.initElements(driver, ProductDetailsPage.class);
    }

    public List<WebElement> findProductCards() {
        return findElements(productCards);
    }

    public Optional<WebElement> findProductCard(String productName) {
        return findElement(By.xpath(String.format(CARD_NAME_PATTERN, productName)));
    }

}
