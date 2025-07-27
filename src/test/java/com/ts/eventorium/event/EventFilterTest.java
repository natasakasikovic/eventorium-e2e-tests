package com.ts.eventorium.event;

import com.ts.eventorium.event.util.EventFilter;
import com.ts.eventorium.util.TestBase;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;

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

        Assert.assertEquals(4, titles.size());

        for (String title : titles)
            assertTrue(title.toLowerCase().contains("sombor"));
    }

    @Test(groups = "event")
    public void testSearchEventsWithNoResults() {
        eventOverviewPage.search("dasdass");

        List<WebElement> elements = eventOverviewPage.getAllEventCards();

        Assert.assertEquals(0, elements.size());
    }

    @Test(groups = "event")
    public void filterEvents() {
        EventFilter filter = new EventFilter("Sombor", "conference", "Corporate Event", "Sombor", 100, LocalDate.now().plusDays(90), LocalDate.now().plusDays(110));
        eventOverviewPage.filter(filter);

        assertTrue(eventOverviewPage.findCard("Sombor Conference").isPresent());

        List<WebElement> events = eventOverviewPage.getAllEventCards();
        Assert.assertEquals(1, events.size());
    }
}