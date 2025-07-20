package com.ts.eventorium.providers;

import org.testng.annotations.DataProvider;

public class BudgetDataProvider {

    public static final String CATEGORY_DECORATION = "Decoration";
    public static final String CATEGORY_GUEST_MANAGEMENT = "Guest Management";
    public static final String CATEGORY_VENUE_BOOKING = "Venue Booking";
    public static final String CATEGORY_ENTERTAINMENT = "Entertainment";

    public static final String PRODUCT_TO_EDIT = "Decorative Balloons";
    public static final String PRODUCT_TO_PURCHASE = "Event Mugs";

    public static final String MANUAL_SERVICE = "Banquet Hall Booking";
    public static final String AUTOMATIC_SERVICE = "Live Band Performance";

    @DataProvider(name = "purchaseScenarios")
    public static Object[][] purchaseScenarios() {
        return new Object[][] {
                {"Event T-Shirts", "Sombor Conference", 15.0, "Successfully purchased product!"},
                {"Event Banner", "Sombor Conference", 40.0, "You do not have enough funds for this purchase!"},
                {"Party Hats", "Wedding in Novi Sad", 10.0, "Solution is already processed"}
        };
    }

    @DataProvider(name = "updateScenarios")
    public static Object[][] updateScenarios() {
        return new Object[][] {
                {0.0, "You do not have enough funds for this purchase/reservation!"},
                {100.0, PRODUCT_TO_EDIT + " has been updated successfully!"},
        };
    }
}
