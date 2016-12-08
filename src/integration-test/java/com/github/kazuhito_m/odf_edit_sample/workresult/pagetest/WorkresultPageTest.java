package com.github.kazuhito_m.odf_edit_sample.workresult.pagetest;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class WorkresultPageTest {

    private static final Logger logger = LoggerFactory.getLogger(WorkresultPageTest.class);

    @Test
    public void 初期表示を確認() {

        logger.debug("IntegrationiTestのこのメソッドが通ってるか確認 : 初期表示を確認()");


        WebDriver driver = FirefoxWebDriverFactroy.create(); //上記Driver.java記載の処理
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/"); // Basic認証が必要なサイトへリクエスト送信する
        driver.manage().window().maximize();
        driver.quit();


//        open("http://localhost:8080/");
//
//        // 検証
//        assertThat("同じ", is("同じ"));
//
//        close();
    }

}
