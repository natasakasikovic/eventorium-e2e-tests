package com.ts.eventorium.event;

import com.ts.eventorium.solution.SolutionDetailsPage;
import com.ts.eventorium.providers.BudgetDataProvider;
import com.ts.eventorium.util.BudgetAction;
import com.ts.eventorium.util.TestBase;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.ts.eventorium.providers.BudgetDataProvider.*;
import static org.junit.Assert.*;

public class BudgetTest extends TestBase {

    private BudgetPlanningPage planningPage;

    @Test(groups = "budget")
    public void testLoadBudgetPlanner() {
        CreateEventPage page = homePage.clickLoginButton().signInAsOrganizer().clickCreateEventButton();

        page.setName("Event " + System.currentTimeMillis());
        page.setDescription("Description");
        page.setEventDate(getFutureDate());
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
        List<WebElement> products = planningPage.search(CATEGORY_DECORATION, 100);

        assertEquals(2, products.size());
        assertTrue(planningPage.findByCardName("Party Hats").isPresent());
        assertTrue(planningPage.findByCardName("Decorative Balloons").isPresent());
    }

    @Test(dependsOnMethods = "testGetBudgetItemSuggestions")
    public void testAddItemsToBudgetPlanner() {
        addToPlanner(CATEGORY_ENTERTAINMENT, AUTOMATIC_SERVICE, 800.0);
        addToPlanner(CATEGORY_DECORATION, PRODUCT_TO_EDIT, 20.0);
        addToPlanner(CATEGORY_GUEST_MANAGEMENT, PRODUCT_TO_PURCHASE, 100.0);
        planningPage.clickPurchasedAndReservedTab();

        assertTrue(planningPage.findNameInTable(AUTOMATIC_SERVICE).isPresent());
        assertTrue(planningPage.findNameInTable(MANUAL_SERVICE).isPresent());
        assertTrue(planningPage.findNameInTable(PRODUCT_TO_EDIT).isPresent());
        assertTrue(planningPage.findNameInTable(PRODUCT_TO_PURCHASE).isPresent());

        assertEquals("800", planningPage.getPlannedAmountInput(AUTOMATIC_SERVICE));
        assertEquals("20", planningPage.getPlannedAmountInput(PRODUCT_TO_EDIT));
        assertEquals("100", planningPage.getPlannedAmountInput(PRODUCT_TO_PURCHASE));

        assertEquals("0.00", planningPage.getSpentAmount(AUTOMATIC_SERVICE));
        assertEquals("0.00", planningPage.getSpentAmount(PRODUCT_TO_EDIT));
        assertEquals("0.00", planningPage.getSpentAmount(PRODUCT_TO_PURCHASE));
    }

    @Test(
            dependsOnMethods = "testAddItemsToBudgetPlanner",
            dataProviderClass = BudgetDataProvider.class,
            dataProvider = "updateScenarios"
    )
    public void testUpdateBudgetItem(double price, String expectedMessage) {
        planningPage.clickPurchasedAndReservedTab();
        planningPage.updatePlannedAmount(PRODUCT_TO_EDIT, price);
        planningPage.clickActionButton(PRODUCT_TO_EDIT, BudgetAction.SAVE);

        assertNotNull(planningPage.findToasterWithMessage(expectedMessage));
    }

    @Test(dependsOnMethods = "testUpdateBudgetItem")
    public void testDeleteBudgetItem() {
        planningPage.clickPurchasedAndReservedTab();
        planningPage.clickActionButton(PRODUCT_TO_EDIT, BudgetAction.DELETE);

        assertNotNull(planningPage.findToasterWithMessage(PRODUCT_TO_EDIT  + " has been deleted successfully!"));
    }

    @Test(dependsOnMethods = "testAddItemsToBudgetPlanner")
    public void testPurchaseFromBudgetPlanner() {
        planningPage.clickPurchasedAndReservedTab();
        planningPage.clickActionButton(PRODUCT_TO_PURCHASE, BudgetAction.PURCHASE);
        assertEquals("Purchased", planningPage.getItemStatus(PRODUCT_TO_PURCHASE));
        assertEquals("4.00", planningPage.getSpentAmount(PRODUCT_TO_PURCHASE));
    }

    @Test(dependsOnMethods = "testAddItemsToBudgetPlanner")
    public void testReserveServiceFromBudgetPlanner() {

    }

    @Test(dependsOnMethods = "testAddItemsToBudgetPlanner")
    public void testReserveServiceFromBudget() {
        planningPage.clickPlannerTab();
    }

    @Test(dependsOnMethods = "testDeleteBudgetItem", groups = "budget")
    public void testPurchaseProductFromBudget() {
        planningPage.clickPlannerTab();
        planningPage.search(CATEGORY_DECORATION, 10);
        planningPage.clickSeeMoreButton(PRODUCT_TO_EDIT).clickPurchaseForBudget();
        planningPage.clickPurchasedAndReservedTab();

        assertTrue(planningPage.findNameInTable(PRODUCT_TO_EDIT).isPresent());
        assertEquals("Purchased", planningPage.getItemStatus(PRODUCT_TO_EDIT));
    }

    private void addToPlanner(String categoryName, String solutionName, double plannedAmount) {
        planningPage.search(categoryName, plannedAmount);
        planningPage.clickSeeMoreButton(solutionName).clickAddToPlanner();
        planningPage.clickPlannerTab();
    }

    private String getFutureDate() {
        return LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("M/d/yyyy"));
    }
}
