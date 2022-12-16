package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.workresult.db;

import com.github.kazuhito_m.odf_edit_sample.Application;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class WorkResultsDaoTest {
    private static final Logger logger = LoggerFactory.getLogger(WorkResultsDaoTest.class);

    @Autowired
    private WorkResultDayDao sut;

    @Test
    public void 勤怠の履歴を全件取得できる() {
        // 実行
        List<WorkResultDayTable> actual = sut.selectAll();
        // 検証
        assertFalse(actual.isEmpty());
        // 初期データでほぼほぼnullじゃない埋め方してるはず、検証。
        for (WorkResultDayTable day : actual) {
            assertTrue(day.userId > 0);
            assertNotNull(day.resultDate);
            assertNotNull(day.startTime);
            assertNotNull(day.endTime);
            assertNotNull(day.breakHour);
            assertNull(day.description);
        }
    }

    @Test
    public void 勤怠の履歴をユーザと日付条件で絞り込みできる() throws ParseException {
        // 事前条件
        Date from = DateUtils.toSqlDate("2016/03/01");
        Date to = DateUtils.toSqlDate("2016/03/31");

        // 実行
        List<WorkResultDayTable> actual = sut.selectByUserAndDay(1, from, to);

        // 検証
        assertEquals(20, actual.size());
        for (WorkResultDayTable day : actual) {
            assertEquals(1, day.userId);
            logger.debug("日付:" + day.resultDate.toString());
        }
    }

    @Test
    public void 勤怠の履歴をユーザ指定で全件取得できる() throws ParseException {
        // 実行
        List<WorkResultDayTable> actual = sut.selectByUser(1);
        // 検証
        assertTrue(actual.size() > 100);  // まあ、ゆうに100件は超えるだろう。
        for (WorkResultDayTable day : actual) {
            assertEquals(1, day.userId);
        }
    }
}
