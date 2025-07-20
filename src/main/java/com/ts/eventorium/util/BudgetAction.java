package com.ts.eventorium.util;

public enum BudgetAction {
    SAVE("save"),
    PURCHASE("shopping_cart"),
    DELETE("delete"),
    RESERVE("calendar_today");

    private final String name;

    BudgetAction(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
