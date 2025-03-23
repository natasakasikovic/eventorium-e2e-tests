package com.ts.eventorium.event;

import com.ts.eventorium.util.TestBase;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class BudgetTest extends TestBase {

    private BudgetPlanningPage planningPage;

    @Test
    public void testLoadBudgetPlanner() {
        CreateEventPage page = homePage.clickLoginButton().signInAsOrganizer().clickCreateEventButton();

        page.setName("Event");
        page.setDescription("Description");
        page.setEventDate("3/23/2025");
        page.setAddress("Address");
        page.setMaxParticipants("100");
        page.selectEventType("Wedding");
        page.selectCity("Sombor");
        page.selectPrivacy("Open");

        planningPage = page.clickBudgetPlanningButton();
        assertNotNull(planningPage);
        assertTrue(planningPage.findCategory("Catering").isPresent());
    }

    @Test(dependsOnMethods = "testLoadBudgetPlanner")
    public void testAddCategory() {

    }

}
