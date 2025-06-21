package com.ts.eventorium.providers;

import org.testng.annotations.DataProvider;

public class BudgetDataProvider {

    @DataProvider(name = "purchaseScenarios")
    public static Object[][] purchaseScenarios() {
        return new Object[][] {
                {"Photo Frames", "Event", 7.0, "Successfully purchased product!"},
                {"Event Banner", "Event", 40.0, "You do not have enough funds for this purchase!"},
                {"Party Hats", "Event", 10.0, "Solution is already processed"}
        };
    }
}
