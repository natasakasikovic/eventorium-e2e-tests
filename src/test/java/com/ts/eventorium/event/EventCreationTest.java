package com.ts.eventorium.event;

import com.ts.eventorium.util.TestBase;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class EventCreationTest extends TestBase {

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
        assertNotNull(createAgendaPage.findActivityForm());
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
        assertNotNull(createAgendaPage.findDialog(expectedError));
        createAgendaPage.closeDialog();
    }

    @Test(groups = "event", dependsOnMethods = "testAddInvalidActivityShowsError")
    public void testRemoveInvalidActivityAndFinishAgenda() {
        createAgendaPage.removeActivityByName("Main activity");
        homePage = createAgendaPage.finishAgenda();

        assertNotNull(createAgendaPage.findDialog("Event created successfully!"));
        createAgendaPage.closeDialog();
        assertNotNull(homePage);
    }

    @Test(groups = "event", dependsOnMethods = "testRemoveInvalidActivityAndFinishAgenda")
    public void testEventIsVisibleToUsers() {
        eventOverviewPage = homePage.clickSeeMoreEvents();
        eventOverviewPage.search(eventName);
        WebElement card = eventOverviewPage.findCard(eventName).orElse(null);
        assertNotNull(card);
    }

    @Test(groups = "event", dependsOnMethods = "testEventIsVisibleToUsers")
    public void testExportGuestListPdf() throws InterruptedException {
        EventDetailsPage detailsPage = eventOverviewPage.clickSeeMoreButton(eventName);
        detailsPage.clickExportGuestList();
        Thread.sleep(3000); // to show that pdf is downloaded
    }

    private String getFutureDate() {
        return LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("M/d/yyyy"));
    }
}

