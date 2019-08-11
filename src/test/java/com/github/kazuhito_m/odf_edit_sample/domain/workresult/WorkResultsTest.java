package com.github.kazuhito_m.odf_edit_sample.domain.workresult;

import com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.text.ParseException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WorkResultsTest {

    private static final Logger logger = LoggerFactory.getLogger(WorkResultsTest.class);

    @Test
    public void 指定した範囲の日付をキーとするMapを取得する() throws ParseException {
        // 事前条件
        Date from = DateUtils.toSqlDate("2016/09/01");
        Date to = DateUtils.toSqlDate("2016/09/30");

        // 実行
        Map<Date, WorkResultRow> actual = WorkResults.createBlankMapBy(from, to);

        // 検証
        assertEquals(30, actual.size());
        int i = 1;
        for (WorkResultRow row : actual.values()) {
            WorkResultDay day = row.getDay();
            if (i == 1) {
                assertEquals(from, day.getResultDate());
            } else if (i == actual.size()) {
                assertEquals(to, day.getResultDate());
            }
            assertEquals(i++, row.getLineNo());
            assertNotNull(day.getResultDate());
            assertNotNull(day.getStartTime());
        }

    }

}
