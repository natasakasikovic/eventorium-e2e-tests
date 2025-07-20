package com.ts.eventorium.event;

import com.ts.eventorium.solution.ProductDetailsPage;
import com.ts.eventorium.solution.ProductOverviewPage;
import com.ts.eventorium.providers.BudgetDataProvider;
import com.ts.eventorium.util.BudgetAction;
import com.ts.eventorium.util.TestBase;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BudgetTest extends TestBase {

    private BudgetPlanningPage planningPage;

    @Test(groups = "budget")
    public void testLoadBudgetPlanner() {
        CreateEventPage page = homePage.clickLoginButton().signInAsOrganizer().clickCreateEventButton();

        page.setName("Event");
        page.setDescription("Description");
        page.setEventDate("4/30/2026");
        page.setAddress("Address");
        page.setMaxParticipants("100");
        page.selectEventType("Wedding");
        page.selectCity("Beograd");
        page.selectPrivacy("Open");

        planningPage = page.clickBudgetPlanningButton();
        assertNotNull(planningPage);
        assertTrue(planningPage.findTabCategory("Catering").isPresent());
    }

    @Test(dependsOnMethods = "testLoadBudgetPlanner", groups = "budget")
    public void testAddCategory() {
        planningPage.addCategory("Event Planning");

        assertTrue(planningPage.findTabCategory("Event Planning").isPresent());
    }

    @Test(dependsOnMethods = "testLoadBudgetPlanner", groups = "budget")
    public void testRemoveCategory() {
        planningPage.removeCategory("Catering");

        assertFalse(planningPage.findTabCategory("Catering").isPresent());
    }

    @Test(dependsOnMethods = "testLoadBudgetPlanner", groups = "budget")
    public void testGetBudgetItemSuggestions() {
        List<WebElement> products = planningPage.search("Decoration", 100);

        assertEquals(2, products.size());
        assertTrue(planningPage.findByCardName("Party Hats").isPresent());
        assertTrue(planningPage.findByCardName("Decorative Balloons").isPresent());
    }

    @Test(dependsOnMethods = "testGetBudgetItemSuggestions")
    public void testAddProductToBudgetPlanner() {
        String partyHats = "Party Hats";
        String decorativeBalloons = "Decorative Balloons";
        planningPage.selectCategory("Decoration");
        planningPage.clickSeeMoreButton(partyHats, ProductDetailsPage.class).clickAddToPlanner();
        planningPage.search("Decoration", 20.0);
        planningPage.clickSeeMoreButton(decorativeBalloons, ProductDetailsPage.class).clickAddToPlanner();
        planningPage.clickPurchasedAndReservedTab();

        assertTrue(planningPage.findNameInTable(partyHats).isPresent());
        assertTrue(planningPage.findNameInTable(decorativeBalloons).isPresent());
//        assertEquals("Planned", planningPage.getItemStatus(productName));
        assertEquals("100", planningPage.getPlannedAmountInput(partyHats));
        assertEquals("20", planningPage.getPlannedAmountInput(decorativeBalloons));
        assertEquals("0.00", planningPage.getSpentAmount(decorativeBalloons));
        assertEquals("0.00", planningPage.getSpentAmount(partyHats));
    }

    @Test(
            dependsOnMethods = "testAddProductToBudgetPlanner",
            dataProviderClass = BudgetDataProvider.class,
            dataProvider = "updateScenarios"
    )
    public void testUpdateBudgetItem(double price, String expectedMessage) {
        String productName = "Party Hats";
        planningPage.clickPurchasedAndReservedTab();
        planningPage.updatePlannedAmount(productName, price);
        planningPage.clickActionButton(productName, BudgetAction.SAVE);

        assertNotNull(planningPage.findToasterWithMessage(expectedMessage));
    }

    @Test(dependsOnMethods = "testUpdateBudgetItem")
    public void testDeleteBudgetItem() {
        planningPage.clickPurchasedAndReservedTab();
        planningPage.clickActionButton("Party Hats", BudgetAction.DELETE);

        assertNotNull(planningPage.findToasterWithMessage("Party Hats has been deleted successfully!"));
    }

    @Test(dependsOnMethods = "testAddProductToBudgetPlanner")
    public void testPurchaseFromBudget() {
        String productName = "Decorative Balloons";
        planningPage.clickPurchasedAndReservedTab();
        planningPage.clickActionButton(productName, BudgetAction.PURCHASE);
        assertEquals("Purchased", planningPage.getItemStatus(productName));
        assertEquals("0.72", planningPage.getSpentAmount(productName));
    }

    @Test(dependsOnMethods = "testDeleteBudgetItem", groups = "budget")
    public void testPurchaseProductFromBudget() {
        String productName = "Event Mugs";
        planningPage.clickPlannerTab();
        planningPage.search("Guest Management", 10);
        planningPage.clickSeeMoreButton(productName, ProductDetailsPage.class).clickPurchaseForBudget();
        planningPage.clickPurchasedAndReservedTab();

        assertTrue(planningPage.findNameInTable(productName).isPresent());
        assertEquals("Purchased", planningPage.getItemStatus(productName));
    }

    @Test(
            dataProviderClass = BudgetDataProvider.class,
            dataProvider = "purchaseScenarios",
            dependsOnMethods = "testPurchaseProductFromBudget"
    )
    public void testPurchaseProduct(String productName, String eventName, double price, String expectedMessage) {
        ProductOverviewPage overviewPage = planningPage.clickHome().clickSeeMoreProducts();
        ProductDetailsPage detailsPage = overviewPage.clickSeeMoreButton(productName);

        detailsPage.purchaseProduct(eventName, price);

        assertNotNull(planningPage.findToasterWithMessage(expectedMessage));
    }
}
