package com.ts.eventorium.providers;

import com.ts.eventorium.event.util.EventFilter;
import org.testng.annotations.DataProvider;

import java.time.LocalDate;
import java.util.List;

public class EventProvider {

    @DataProvider(name = "provideEventFilters")
    public static Object[][] provideEventFilters() {
        return new Object[][]{
                { new EventFilter(null, "birthday", null, null, null, null, null), List.of("Birthday Bash in Sombor", "Birthday Celebration in Beograd", "Kraljevo Birthday Party", "Birthday Extravaganza in Novi Sad")},
                { new EventFilter(null, null, null, "Trebinje", null, null, null), List.of("Trebinje Wedding Ceremony")},
                { new EventFilter(null, null, null, null, 50, null, null), List.of("Corporate Event in Novi Sad", "Kraljevo Birthday Party")},
                { new EventFilter(null, null, null, null, null, LocalDate.now().plusDays(99), LocalDate.now().plusDays(100)), List.of("Sombor Conference", "Wedding Expo in Novi Sad", "Kraljevo Birthday Party", "Birthday Extravaganza in Novi Sad")}
        };
    }
}