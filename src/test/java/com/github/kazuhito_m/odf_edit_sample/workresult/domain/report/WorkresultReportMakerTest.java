package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;


import com.github.kazuhito_m.odf_edit_sample.fw.util.DateUtils;
import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.workresult.view.WorkresultRow;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WorkresultReportMakerTest {

    private static final Logger logger = LoggerFactory.getLogger(WorkresultReportMakerTest.class);

    private WorkresultReportMaker sut;

    @Before
    public void setup() {
        sut = new WorkresultReportMaker();
    }

    @Test
    public void ODSファイルをテンプレートとして置き換える値が置き換えられる_一覧以外() throws IOException, ParseException {
        // 事前準備
        User user = new User();
        user.name = "Kazuhito Miura";
        List<WorkresultRow> rows = new ArrayList<>();
        rows.add(new WorkresultRow(1, DateUtils.toSqlDate("2016/09/01")));
        rows.add(new WorkresultRow(2, DateUtils.toSqlDate("2016/09/02")));

        // 実行
        File actual = sut.makeReport(user, rows);

        // 検証
        assertThat(actual.exists(), is(true));
        logger.debug("レポートの一時ファイル : " + actual.getCanonicalPath());

    }

}
