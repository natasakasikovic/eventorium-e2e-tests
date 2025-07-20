package com.ts.eventorium.providers;

import org.testng.annotations.DataProvider;

public class BudgetDataProvider {

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
                {100.0, "Party Hats has been updated successfully!"},
        };
    }
}
