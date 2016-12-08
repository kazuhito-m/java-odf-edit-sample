package com.github.kazuhito_m.odf_edit_sample.workresult.pagetest;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WorkresultPageTest {

    private static final Logger logger = LoggerFactory.getLogger(WorkresultPageTest.class);

    @Before
    public void setUp() {
        // Selenideに「自作のFirefoxドライバー」をセット。
        WebDriverRunner.setWebDriver(FirefoxWebDriverFactroy.create());
    }

    @Test
    public void 初期表示を確認() {

        logger.debug("IntegrationiTestのこのメソッドが通ってるか確認 : 初期表示を確認()");


        open("http://localhost:8080/");

        // 検証
        assertThat("同じ", is("同じ"));

    }

    @After
    public void tearDown() {
        close();
    }

}
