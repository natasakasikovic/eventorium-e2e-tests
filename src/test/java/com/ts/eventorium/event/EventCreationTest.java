package com.ts.eventorium.event;

import com.ts.eventorium.util.TestBase;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class EventCreationTest extends TestBase {

    private CreateEventPage page;
    private BudgetPlanningPage budgetPlanningPage;
    CreateAgendaPage createAgendaPage;

    @Test(groups = "event")
    public void testEventCreation() {
        page = homePage.clickLoginButton().signInAsOrganizer().clickCreateEventButton();

        page.setName("Event");
        page.setDescription("Description");
        page.setEventDate("4/30/2022"); // date in past
        page.setAddress("Address");
        page.setMaxParticipants("100");
        page.selectEventType("Wedding");
        page.selectCity("Beograd");
        page.selectPrivacy("Open");

        page.clickBudgetPlanningButton();
        assertNotNull(page.findDialog("Event date must not be in the past"));
        page.closeDialog();

        page.setEventDate(getFutureDate());
        budgetPlanningPage = page.clickBudgetPlanningButton();
        assertTrue(budgetPlanningPage.findTabCategory("Catering").isPresent());
        createAgendaPage = budgetPlanningPage.clickAgendaCreationButton();
        assertNotNull(createAgendaPage.findActivityForm());
    }

    @Test(groups = "event", dependsOnMethods = "testEventCreation")
    public void testAgendaCreation() {
        createAgendaPage.fillActivity("Welcome speech", "Main Hall", "Introductory words", "10:00 AM", "10:30 AM");
        createAgendaPage.clickAddActivity();
        assertTrue(createAgendaPage.isActivityAdded("Welcome speech"));

        createAgendaPage.fillActivity("Main activity", "Main Hall", "About event", "2:00 PM", "1:00 PM");
        createAgendaPage.clickAddActivity();
        createAgendaPage.finishAgenda();
        String expectedError = "Invalid time range for activity 'Main activity': end time (13:00) must be after start time (14:00).";
        assertNotNull(createAgendaPage.findDialog(expectedError));
        createAgendaPage.closeDialog();

        createAgendaPage.removeActivityByName("Main activity");
        createAgendaPage.finishAgenda();

        assertNotNull(createAgendaPage.findDialog("Event created successfully!"));
    }

    private String getFutureDate() {
        return LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("M/d/yyyy"));
    }
}
