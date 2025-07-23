package com.ts.eventorium.event;

import com.ts.eventorium.util.TestBase;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

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
            Assert.assertTrue(title.toLowerCase().contains("sombor"));
    }

    @Test(groups = "event")
    public void testSearchEventsWithNoResults() {
        eventOverviewPage.search("dasdass");

        List<WebElement> elements = eventOverviewPage.getAllEventCards();

        Assert.assertEquals(0, elements.size());
    }
}