package com.github.kazuhito_m.odf_edit_sample.workresult.pagetest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirefoxWebDriverFactroy {

    private static final Logger logger = LoggerFactory.getLogger(FirefoxWebDriverFactroy.class);

    public static WebDriver create() {

        String geckoDrivePath = WorkresultPageTest.class.getResource("geckodriver").getPath();
        logger.debug("geckoDrivePath : " + geckoDrivePath);
        System.setProperty("webdriver.gecko.driver", geckoDrivePath);
        System.setProperty("selenide.reports", "build/screenshot/");

        // https://id:pw@url/でアクセス可能なFireFox用Profileを生成・ロードする
        ProfilesIni profile = new ProfilesIni();
        FirefoxProfile myprofile = profile.getProfile("SeleniumProfile");

        // プロファイルをセットする
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setJavascriptEnabled(true);

        // marionetteをtrueにする
        capabilities.setCapability("marionette", true);

        // capabilitiesにプロファイルを指定してドライバを生成する
        WebDriver driver = new MarionetteDriver(capabilities);

        return driver;

    }


}
