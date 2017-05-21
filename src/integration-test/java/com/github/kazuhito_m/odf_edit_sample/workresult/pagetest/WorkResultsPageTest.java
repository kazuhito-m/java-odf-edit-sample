package com.github.kazuhito_m.odf_edit_sample.workresult.pagetest;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WorkResultsPageTest {

    // FIXME Firefox v.47 の問題を解決するため、FirefoxWebDriverFactroryにて「一応動くように」したが、今度は「２度newすると接続拒否される」というバグを解決できなかった。
    // FIXME 現在「テストケースを１つに限定」することでなんとかしているが、諸々解決したし。

    private static final Logger logger = LoggerFactory.getLogger(WorkResultsPageTest.class);

    @Before
    public void setUp() {
        // Selenideに「自作のFirefoxドライバー」をセット。
        WebDriverRunner.setWebDriver(FirefoxWebDriverFactroy.create());
    }

    @Test
    public void 月度を選択するとその月の勤怠実績データを表示() {

        logger.debug("URL指定でトップページを開く。");
        open(EnvironmentModerator.getAppRootUrl());

        logger.debug("ハードコピー取得");
        screenshot(this.getClass().getSimpleName() + "/default-top-page");

        logger.debug("初期表示の行数確認");
        int rows = $("#workResultDetails").findElements(By.tagName("tr")).size();

        assertThat("初期表示時は０行表示", rows, is(0));

        logger.debug("月度を選択");
        $("#monthSelect").selectOption("2014/11");

        sleep(1000);    // 応答は１秒以内、ソレ以上かかってたらこけることとする。

        logger.debug("ハードコピー取得");
        screenshot(this.getClass().getSimpleName() + "/select-month-operation");

        logger.debug("初期表示の行数確認");
        rows = $("#workResultDetails").findElements(By.tagName("tr")).size();

        assertThat("11月度を選択したので30日間", rows, is(30));

    }

    @After
    public void tearDown() {
        close();
    }

}
