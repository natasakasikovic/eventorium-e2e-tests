package com.ts.eventorium.event;

import com.ts.eventorium.util.TestBase;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.*;

public class EventTest extends TestBase {

    private CreateEventPage page;
    private BudgetPlanningPage budgetPlanningPage;
    private CreateAgendaPage createAgendaPage;
    private EventOverviewPage eventOverviewPage;
    private String eventName = "Test Event " + System.currentTimeMillis();

    @Test(groups = "event")
    public void testInvalidEventDateShowsDialog() {
        page = homePage.clickLoginButton().signInAsOrganizer().clickCreateEventButton();

        page.selectEventType("Wedding");
        page.setName(eventName);
        page.setDescription("Description");
        page.setAddress("Address");
        page.setMaxParticipants("100");
        page.selectCity("Beograd");
        page.selectPrivacy("Open");
        page.setEventDate("4/30/2022"); // Past date

        page.clickBudgetPlanningButton();
        assertNotNull(page.findDialog("Event date must not be in the past"));
        page.closeDialog();
    }

    @Test(groups = "event", dependsOnMethods = "testInvalidEventDateShowsDialog")
    public void testValidEventDateAllowsProceeding() {
        page.setEventDate(getFutureDate());
        budgetPlanningPage = page.clickBudgetPlanningButton();

        assertTrue(budgetPlanningPage.findTabCategory("Catering").isPresent());
    }

    @Test(groups = "event", dependsOnMethods = "testValidEventDateAllowsProceeding")
    public void testAgendaPageLoadsSuccessfully() {
        createAgendaPage = budgetPlanningPage.clickAgendaCreationButton();
        assertTrue(createAgendaPage.findActivityForm().isPresent());
    }

    @Test(groups = "event", dependsOnMethods = "testAgendaPageLoadsSuccessfully")
    public void testAddValidActivity() {
        createAgendaPage.fillActivity("Welcome speech", "Main Hall", "Introductory words", "10:00 AM", "10:30 AM");
        createAgendaPage.clickAddActivity();

        assertTrue(createAgendaPage.isActivityAdded("Welcome speech"));
    }

    @Test(groups = "event", dependsOnMethods = "testAddValidActivity")
    public void testAddInvalidActivityShowsError() {
        createAgendaPage.fillActivity("Main activity", "Main Hall", "About event", "2:00 PM", "1:00 PM");
        createAgendaPage.clickAddActivity();
        createAgendaPage.finishAgenda();

        String expectedError = "Invalid time range for activity 'Main activity': end time (13:00) must be after start time (14:00).";
        assertTrue(createAgendaPage.findDialog(expectedError).isPresent());
        createAgendaPage.closeDialog();
    }

    @Test(groups = "event", dependsOnMethods = "testAddInvalidActivityShowsError")
    public void testRemoveInvalidActivityAndFinishAgenda() {
        createAgendaPage.removeActivityByName("Main activity");
        homePage = createAgendaPage.finishAgenda();

        assertTrue(createAgendaPage.findDialog("Event created successfully!").isPresent());
        createAgendaPage.closeDialog();
        assertNotNull(homePage);
    }

    @Test(groups = "event", dependsOnMethods = "testRemoveInvalidActivityAndFinishAgenda")
    public void testEventIsVisibleToUsers() {
        eventOverviewPage = homePage.clickSeeMoreEvents();
        eventOverviewPage.search(eventName);
        assertTrue(eventOverviewPage.findCard(eventName).isPresent());
    }

    @Test(groups = "event", dependsOnMethods = "testEventIsVisibleToUsers")
    public void testExportGuestListPdf() {
        EventDetailsPage detailsPage = eventOverviewPage.clickSeeMoreButton(eventName);

        assertTrue(detailsPage.getExportButton().isPresent());
        detailsPage.clickExportGuestList();
    }

    @Test(groups = "event")
    public void searchEvents() {
        eventOverviewPage = homePage.clickSeeMoreEvents();
        eventOverviewPage.search("sombor");

        List<String> titles = eventOverviewPage.getAllEventTitles();

        Assert.assertEquals(4, titles.size());

        for (String title : titles)
            Assert.assertTrue(title.toLowerCase().contains("sombor"));
    }

    private String getFutureDate() {
        return LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("M/d/yyyy"));
    }
}

