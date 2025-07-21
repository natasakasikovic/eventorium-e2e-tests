package com.ts.eventorium.event;

import com.ts.eventorium.solution.SolutionDetailsPage;
import com.ts.eventorium.providers.BudgetDataProvider;
import com.ts.eventorium.util.BudgetAction;
import com.ts.eventorium.util.TestBase;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.ts.eventorium.providers.BudgetDataProvider.*;
import static org.junit.Assert.*;

public class BudgetTest extends TestBase {

    private BudgetPlanningPage planningPage;

    @BeforeClass(groups = "budget")
    public void loadBudgetPlanner() {
        CreateEventPage page = homePage.clickLoginButton().signInAsOrganizer().clickCreateEventButton();
        fillEventForm(page);
        planningPage = page.clickBudgetPlanningButton();
        assertNotNull(planningPage);
        assertTrue(planningPage.findTabCategory("Catering").isPresent());
    }

    @Test
    public void testAddCategory() {
        planningPage.addCategory("Event Planning");

        assertTrue(planningPage.findTabCategory("Event Planning").isPresent());
    }

    @Test
    public void testRemoveCategory() {
        planningPage.removeCategory("Catering");

        assertFalse(planningPage.findTabCategory("Catering").isPresent());
    }

    @Test
    public void testGetBudgetItemSuggestions() {
        List<WebElement> products = planningPage.search(CATEGORY_DECORATION, 100);

        assertEquals(2, products.size());
        assertTrue(planningPage.findByCardName("Party Hats").isPresent());
        assertTrue(planningPage.findByCardName("Decorative Balloons").isPresent());
    }

    @Test(dependsOnMethods = "testGetBudgetItemSuggestions")
    public void testAddSingleItemToBudgetPlanner() {
        planningPage.clickPlannerTab();
        addToPlanner(CATEGORY_ENTERTAINMENT, AUTOMATIC_SERVICE, 800.0);
        planningPage.clickPurchasedAndReservedTab();

        assertTrue(planningPage.findNameInTable(AUTOMATIC_SERVICE).isPresent());
        assertEquals("800", planningPage.getPlannedAmountInput(AUTOMATIC_SERVICE));
        assertEquals("0.00", planningPage.getSpentAmount(AUTOMATIC_SERVICE));
        assertEquals(CATEGORY_ENTERTAINMENT, planningPage.getCategory(AUTOMATIC_SERVICE));
    }

    @Test(dependsOnMethods = "testAddSingleItemToBudgetPlanner")
    public void testAddMultipleItemsToBudgetPlanner() {
        planningPage.clickPlannerTab();
        addToPlanner(CATEGORY_DECORATION, PRODUCT_TO_EDIT, 20.0);
        addToPlanner(CATEGORY_GUEST_MANAGEMENT, PRODUCT_TO_PURCHASE, 100.0);
        planningPage.clickPurchasedAndReservedTab();

        assertTrue(planningPage.findNameInTable(PRODUCT_TO_EDIT).isPresent());
        assertTrue(planningPage.findNameInTable(PRODUCT_TO_PURCHASE).isPresent());

        assertEquals("20", planningPage.getPlannedAmountInput(PRODUCT_TO_EDIT));
        assertEquals("100", planningPage.getPlannedAmountInput(PRODUCT_TO_PURCHASE));
    }

    @Test(
            dependsOnMethods = "testAddMultipleItemsToBudgetPlanner",
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

    @Test(dependsOnMethods = "testAddMultipleItemsToBudgetPlanner")
    public void testPurchaseFromBudgetPlanner() {
        planningPage.clickPurchasedAndReservedTab();
        planningPage.clickActionButton(PRODUCT_TO_PURCHASE, BudgetAction.PURCHASE);
        assertTrue(planningPage.checkItemStatus(PRODUCT_TO_PURCHASE, "Purchased"));
        assertEquals("4.00", planningPage.getSpentAmount(PRODUCT_TO_PURCHASE));
    }

    @Test(dependsOnMethods = "testAddMultipleItemsToBudgetPlanner")
    public void testReserveServiceFromBudgetPlanner() {
        planningPage.clickPurchasedAndReservedTab();
        planningPage.clickActionButton(AUTOMATIC_SERVICE, BudgetAction.RESERVE);
        planningPage.makeReservation("01:00 PM", "03:00 PM");

        assertTrue(planningPage.checkItemStatus(AUTOMATIC_SERVICE, "Reserved"));
        assertEquals("800.00", planningPage.getPlannedAmount(AUTOMATIC_SERVICE));
        assertEquals("720.00", planningPage.getSpentAmount(AUTOMATIC_SERVICE));
    }

    @Test(dependsOnMethods = "testAddMultipleItemsToBudgetPlanner")
    public void testReserveServiceFromBudget() {
        planningPage.clickPlannerTab();
        planningPage.search(CATEGORY_VENUE_BOOKING, 1000.0);
        SolutionDetailsPage detailsPage = planningPage.clickSeeMoreButton(MANUAL_SERVICE);
        detailsPage.clickReserveButton();
        detailsPage.makeReservation("07:00 AM", "01:00 PM");
        planningPage.clickPurchasedAndReservedTab();

        assertTrue(planningPage.checkItemStatus(MANUAL_SERVICE, "Pending"));
        assertEquals("1000", planningPage.getPlannedAmountInput(MANUAL_SERVICE));
        assertEquals("0.00", planningPage.getSpentAmount(MANUAL_SERVICE));
    }

    @Test(dependsOnMethods = "testDeleteBudgetItem", groups = "budget")
    public void testPurchaseProductFromBudget() {
        planningPage.clickPlannerTab();
        planningPage.search(CATEGORY_DECORATION, 10);
        planningPage.clickSeeMoreButton(PRODUCT_TO_EDIT).clickPurchaseForBudget();
        planningPage.clickPurchasedAndReservedTab();

        assertTrue(planningPage.findNameInTable(PRODUCT_TO_EDIT).isPresent());
        assertTrue(planningPage.checkItemStatus(PRODUCT_TO_EDIT, "Purchased"));
    }

    private void addToPlanner(String categoryName, String solutionName, double plannedAmount) {
        planningPage.search(categoryName, plannedAmount);
        planningPage.clickSeeMoreButton(solutionName).clickAddToPlanner();
        planningPage.clickPlannerTab();
    }

    private void fillEventForm(CreateEventPage page) {
        page.setName("Event " + System.currentTimeMillis());
        page.setDescription("Description");
        page.setEventDate(getFutureDate());
        page.setAddress("Address");
        page.setMaxParticipants("100");
        page.selectEventType("Wedding");
        page.selectCity("Beograd");
        page.selectPrivacy("Open");
    }

    private String getFutureDate() {
        return LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("M/d/yyyy"));
    }
}
