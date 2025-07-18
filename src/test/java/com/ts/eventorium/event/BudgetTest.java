package com.ts.eventorium.event;

import com.ts.eventorium.solution.ProductDetailsPage;
import com.ts.eventorium.solution.ProductOverviewPage;
import com.ts.eventorium.providers.BudgetDataProvider;
import com.ts.eventorium.util.TestBase;
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
    public void testSearchServices() {
        List<WebElement> services = planningPage.search("Catering", 1000);

        assertEquals(2, services.size());
        assertTrue(planningPage.findByCardName("Catering Service").isPresent());
        assertTrue(planningPage.findByCardName("Buffet Catering").isPresent());
    }

    @Test(dependsOnMethods = "testLoadBudgetPlanner", groups = "budget")
    public void testSearchProducts() {
        List<WebElement> products = planningPage.search("Decoration", 100);

        assertEquals(2, products.size());
        assertTrue(planningPage.findByCardName("Party Hats").isPresent());
        assertTrue(planningPage.findByCardName("Decorative Balloons").isPresent());
    }

    @Test(dependsOnMethods = "testSearchProducts", groups = "budget")
    public void testPurchaseProductFromBudget() {
        planningPage.selectCategory("Decoration");
        BudgetPlanningPage page = planningPage.clickSeeMoreButton("Party Hats").clickPurchaseForBudget();

        assertNotNull(page);
        page.clickPurchasedAndReservedTab();

        assertTrue(page.findNameInTable("Party Hats").isPresent());
        assertEquals("Purchased", page.getItemStatus("Party Hats"));
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
