package com.github.kazuhito_m.odf_edit_sample.workresult.dao;

import com.github.kazuhito_m.odf_edit_sample.Example;
import com.github.kazuhito_m.odf_edit_sample.workresult.entity.WorkresultDay;
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
import java.text.SimpleDateFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Example.class})
@Transactional
public class WorkresultDaoTest {

    private static final Logger logger = LoggerFactory.getLogger(WorkresultDaoTest.class);
    @Autowired
    private WorkresultDayDao sut;

    @Test
    public void 勤怠の履歴を全件取得できる() {
        // 実行
        List<WorkresultDay> actual = sut.selectAll();
        // 検証
        assertThat(actual.isEmpty(), is(false));
        // 初期データでほぼほぼnullじゃない埋め方してるはず、検証。
        for (WorkresultDay day : actual) {
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date from = new Date(sdf.parse("2016/03/01").getTime());
        Date to = new Date(sdf.parse("2016/03/31").getTime());

        // 実行
        List<WorkresultDay> actual = sut.selectByUserAndDay(1, from, to);

        // 検証
        assertThat(actual.size(), is(20));
        for (WorkresultDay day : actual) {
            assertThat(day.userId, is(1));
            logger.debug("日付:" + day.resultDate.toString());
        }
    }

}
