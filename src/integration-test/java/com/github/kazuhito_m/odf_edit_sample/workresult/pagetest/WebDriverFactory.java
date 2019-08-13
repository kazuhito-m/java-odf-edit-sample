package com.github.kazuhito_m.odf_edit_sample.workresult.pagetest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {
    public WebDriver create() {
        System.setProperty("webdriver.chrome.driver", driverPath());
        System.setProperty("selenide.reports", "build/screenshot/");
        WebDriver driver = new ChromeDriver();
        return driver;
    }

    private String driverPath() {
        String resourceKey = analyzeOs() + "/chromedriver";
        return getClass().getResource(resourceKey).getPath();
    }

    private String analyzeOs() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("mac")) return "mac";
        if (os.startsWith("win")) return "win32";
        return "linux";
    }
}
