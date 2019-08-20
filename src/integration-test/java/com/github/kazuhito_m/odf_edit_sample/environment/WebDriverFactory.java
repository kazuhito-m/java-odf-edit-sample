package com.github.kazuhito_m.odf_edit_sample.environment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {
    private static final String ENV_VAR_NAME_SELENIUM_HOST = "REMOTE_SELENIUM_HOST";

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverFactory.class);

    public WebDriver create() throws MalformedURLException {
        System.setProperty("selenide.reports", "build/screenshot/");
        if (requireRemoteDriver()) return createRemoteChromeDriver();
        return createLocalChromeDriver();
    }

    private boolean requireRemoteDriver() {
        return !remoteSeleniumHostOnEnv().isEmpty();
    }

    private String remoteSeleniumHostOnEnv() {
        if (!System.getenv().containsKey(ENV_VAR_NAME_SELENIUM_HOST)) return "";
        return System.getenv(ENV_VAR_NAME_SELENIUM_HOST);
    }

    private WebDriver createRemoteChromeDriver() throws MalformedURLException {
        String host = remoteSeleniumHostOnEnv();
        String uri = String.format("http://%s:4444/wd/hub", host);
        LOGGER.info("リモートのSeleniumホストが指定されました:{}", uri);
        return new RemoteWebDriver(new URL(uri), DesiredCapabilities.chrome());
    }

    private WebDriver createLocalChromeDriver() {
        System.setProperty("webdriver.chrome.driver", driverPath());
        LOGGER.info("Seleniumはローカルマシンで実行します。");
        return new ChromeDriver();
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
