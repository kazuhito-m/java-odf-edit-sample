package com.github.kazuhito_m.odf_edit_sample.domain.workresult.report;

import com.github.kazuhito_m.odf_edit_sample.App;
import com.github.kazuhito_m.odf_edit_sample.domain.report.SpreadSheetRepository;
import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultDay;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultRow;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResults;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.class})
public class WorkResultsReportMakerTest {

    private static final Logger logger = LoggerFactory.getLogger(WorkResultsReportMakerTest.class);

    @Autowired
    private SpreadSheetRepository spreadSheetRepository;

    @Test
    public void ODSファイルをテンプレートとして置き換える値が置き換えられる_一覧以外() throws IOException, ParseException {
        // 事前準備
        User user = new User(1, "Kazuhito Miura", "", "", "", "");
        List<WorkResultRow> rows = new ArrayList<>();
        add(rows, 1, "2016/09/01");
        add(rows, 2, "2016/09/02");
        WorkResults results = new WorkResults(rows);

        // 実行
        final WorkResultReportMaker sut = new WorkResultReportMaker(user, "2016/09", results);
        File actual = spreadSheetRepository.makeReport(sut); //sut.writeContent(ods);

        // 検証
        assertThat(actual.exists(), is(true));
        logger.debug("レポートの一時ファイル : " + actual.getCanonicalPath());
    }

    private void add(List<WorkResultRow> rows, int rowNo, String resultDate) throws ParseException {
        WorkResultDay day = WorkResultDay.prototype(DateUtils.toSqlDate(resultDate));
        WorkResultRow row = new WorkResultRow(rowNo, day);
        rows.add(row);
    }

}
