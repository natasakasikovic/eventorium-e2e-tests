package com.ts.eventorium.event;

import com.ts.eventorium.util.TestBase;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BudgetTest extends TestBase {

    private BudgetPlanningPage planningPage;

    @Test
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

    @Test(dependsOnMethods = "testLoadBudgetPlanner")
    public void testAddCategory() {
        planningPage.addCategory("Event Planning");

        assertTrue(planningPage.findTabCategory("Event Planning").isPresent());
    }

    @Test(dependsOnMethods = "testLoadBudgetPlanner")
    public void testRemoveCategory() {
        planningPage.removeCategory("Catering");

        assertFalse(planningPage.findTabCategory("Catering").isPresent());
    }

    @Test(dependsOnMethods = "testLoadBudgetPlanner")
    public void testSearchServices() {
        List<WebElement> services = planningPage.searchServices("Catering", 1000);

        assertEquals(1, services.size());
        assertTrue(planningPage.findByCardName("Catering Service").isPresent());
    }

    @Test(dependsOnMethods = "testLoadBudgetPlanner")
    public void testSearchService_notServiceFound() {
        List<WebElement> services = planningPage.searchServices("Catering", 0);

        assertEquals(0, services.size());
    }

    @Test(dependsOnMethods = "testLoadBudgetPlanner")
    public void testSearchProducts() {
        List<WebElement> products = planningPage.searchProducts("Decoration", 100);

        assertEquals(2, products.size());
        assertTrue(planningPage.findByCardName("Party Hats").isPresent());
        assertTrue(planningPage.findByCardName("Decorative Balloons").isPresent());
    }

    @Test(dependsOnMethods = "testLoadBudgetPlanner")
    public void testSearchProducts_notProductsFound() {
        List<WebElement> services = planningPage.searchProducts("Photography", 0);

        assertEquals(0, services.size());
    }
}
