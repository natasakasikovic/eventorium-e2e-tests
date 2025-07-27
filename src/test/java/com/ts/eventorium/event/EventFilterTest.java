package com.ts.eventorium.event;

import com.ts.eventorium.event.util.EventFilter;
import com.ts.eventorium.providers.EventProvider;
import com.ts.eventorium.util.TestBase;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class EventFilterTest extends TestBase {

    private EventOverviewPage eventOverviewPage;

    @BeforeClass
    public void openEventOverviewPage() {
        eventOverviewPage = homePage.clickSeeMoreEvents();
    }

    @Test(groups = "event")
    public void testSearchEventsWithResults() {
        eventOverviewPage.search("sombor");

        List<String> titles = eventOverviewPage.getAllEventTitles();

        assertEquals(4, titles.size());

        for (String title : titles)
            assertTrue(title.toLowerCase().contains("sombor"));
    }

    @Test(groups = "event")
    public void testSearchEventsWithNoResults() {
        eventOverviewPage.search("dasdass");

        List<WebElement> elements = eventOverviewPage.getAllEventCards();

        assertEquals(0, elements.size());
    }

    @Test(groups = "event")
    public void testSearchWithEmptyString() {
        eventOverviewPage.search("");

        List<WebElement> events = eventOverviewPage.getAllEventCards();
        assertFalse(events.isEmpty());
    }

    @Test(groups = "event")
    public void testFilterWithValidCriteriaReturnsSingleExpectedEvent() {
        EventFilter filter = new EventFilter("Sombor", "conference", "Corporate Event", "Sombor", 100, LocalDate.now().plusDays(90), LocalDate.now().plusDays(110));
        eventOverviewPage.filter(filter);

        assertTrue(eventOverviewPage.findCard("Sombor Conference").isPresent());
        assertEquals(1, eventOverviewPage.getAllEventCards().size());
    }

    @Test(dataProviderClass = EventProvider.class, dataProvider = "provideEventFilters", groups = "event")
    public void testFilterReturnsExpectedEvents(EventFilter filter, List<String> expectedTitles) {
        eventOverviewPage.filter(filter);

        for (String expected : expectedTitles)
            assertTrue(eventOverviewPage.findCard(expected).isPresent());

        assertEquals(expectedTitles.size(), eventOverviewPage.getAllEventCards().size());
    }
}