package com.github.kazuhito_m.odf_edit_sample.workresult.pagetest;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class WorkResultsPageTest {
   private static final Logger logger = LoggerFactory.getLogger(WorkResultsPageTest.class);

    @BeforeAll
    public static void setUp() {
        // Selenideに「自作のWebドライバー」をセット。
        WebDriverFactory factory = new WebDriverFactory();
        WebDriverRunner.setWebDriver(factory.create());
    }

    @Test
    public void 初期表示は勤怠実績データはゼロ件で表示() {
        logger.debug("URL指定でトップページを開く。");
        open(new EnvironmentModerator().appRootUrl());

        logger.debug("ハードコピー取得");
        hardCopy("default-top-page");

        logger.debug("初期表示の行数確認");
        int rows = $("#workResultDetails").findElements(By.tagName("tr")).size();

        assertEquals(0, rows, "初期表示時は０行表示");
    }

    @Test
    public void 月度を選択するとその月の勤怠実績データを表示() {
        logger.debug("URL指定でトップページを開く。");
        open(new EnvironmentModerator().appRootUrl());

        logger.debug("月度を選択");
        $("#monthSelect").selectOption("2014/11");

        sleep(1000);    // 応答は１秒以内、ソレ以上かかってたらこけることとする。

        logger.debug("ハードコピー取得");
        hardCopy("select-month-operation");

        logger.debug("初期表示の行数確認");
        int rows = $("#workResultDetails").findElements(By.tagName("tr")).size();

        assertEquals(30, rows, "11月度を選択したので30日間");
    }

    private void hardCopy(String name) {
        screenshot(this.getClass().getSimpleName() + "/" + name);
    }

    @AfterAll
    public static void tearDown() {
        WebDriverRunner.getWebDriver().close(); // 本来であれば、下の構文でとじるのだけど…。
        close();
    }
}
