package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;


import com.github.kazuhito_m.odf_edit_sample.Example;
import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.workresult.view.WorkresultRow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Example.class})
public class WorkresultReportMakerTest {

    private static final Logger logger = LoggerFactory.getLogger(WorkresultReportMakerTest.class);

    private WorkresultReportMaker sut;

    @Before
    public void setup() {
        sut = new WorkresultReportMaker();
    }

    @Test
    public void ODSファイルをテンプレートとして置き換える値が置き換えられる_一覧以外() throws IOException {
        // 事前準備
        User user = new User();
        user.name = "Kazuhito Miura";
        String month = "2016/09";
        List<WorkresultRow> rows = Collections.emptyList();

        // 実行
        File actual = sut.makeReport(user, month, rows);

        // 検証
        assertThat(actual.exists(), is(true));
        logger.debug("レポートの一時ファイル : " + actual.getCanonicalPath());

    }

}
