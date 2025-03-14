package com.ts.eventorium.tests;

import org.testng.annotations.Test;

public class ExampleTest extends TestBase {

    @Test
    public void test() {
        driver.get("https://www.google.com");
    }
}
