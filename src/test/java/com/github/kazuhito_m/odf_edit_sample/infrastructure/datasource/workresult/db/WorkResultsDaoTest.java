package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.workresult.db;

import com.github.kazuhito_m.odf_edit_sample.App;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.class})
@Transactional
public class WorkResultsDaoTest {

    private static final Logger logger = LoggerFactory.getLogger(WorkResultsDaoTest.class);
    @Autowired
    private WorkResultDayDao sut;

    @Test
    public void 勤怠の履歴を全件取得できる() {
        // 実行
        List<WorkResultDay> actual = sut.selectAll();
        // 検証
        assertThat(actual.isEmpty(), is(false));
        // 初期データでほぼほぼnullじゃない埋め方してるはず、検証。
        for (WorkResultDay day : actual) {
            assertThat(day.userId > 0, is(true));
            assertThat(day.resultDate, is(notNullValue()));
            assertThat(day.startTime, is(notNullValue()));
            assertThat(day.endTime, is(notNullValue()));
            assertThat(day.breakHour, is(notNullValue()));
            assertThat(day.description, is(nullValue()));
            //            logger.debug("日付:" + day.resultDate.toString());
        }
    }

    @Test
    public void 勤怠の履歴をユーザと日付条件で絞り込みできる() throws ParseException {
        // 事前条件
        Date from = DateUtils.toSqlDate("2016/03/01");
        Date to = DateUtils.toSqlDate("2016/03/31");

        // 実行
        List<WorkResultDay> actual = sut.selectByUserAndDay(1, from, to);

        // 検証
        assertThat(actual.size(), is(20));
        for (WorkResultDay day : actual) {
            assertThat(day.userId, is(1));
            logger.debug("日付:" + day.resultDate.toString());
        }
    }

    @Test
    public void 勤怠の履歴をユーザ指定で全件取得できる() throws ParseException {
        // 実行
        List<WorkResultDay> actual = sut.selectByUser(1);
        // 検証
        assertThat(actual.size() > 100, is(true));  // まあ、ゆうに100件は超えるだろう。
        for (WorkResultDay day : actual) {
            assertThat(day.userId, is(1));
        }
    }


}
