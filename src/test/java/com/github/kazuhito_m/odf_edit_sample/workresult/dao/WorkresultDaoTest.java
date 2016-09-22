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

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Example.class})
@Transactional
public class WorkresultDaoTest {

    @Autowired
    private WorkresultDayDao sut;

    private static final Logger logger = LoggerFactory.getLogger(WorkresultDaoTest.class);

    @Test
    public void 勤怠の履歴を全件取得できる() {
        // 実行
        List<WorkresultDay> actual = sut.selectAll();
        // 検証
        assertThat(actual.isEmpty(), is(false));
        // 初期データでほぼほぼnullじゃない埋め方してるはず、検証。ｓ
        for (WorkresultDay day : actual) {
            assertThat(day.userId > 0 , is(true));
            assertThat(day.resultDate , is(notNullValue()));
            assertThat(day.startTime , is(notNullValue()));
            assertThat(day.endTime , is(notNullValue()));
            assertThat(day.breakHour , is(notNullValue()));
            assertThat(day.description , is(nullValue()));
            //            logger.debug("日付:" + day.resultDate.toString());
        }
    }

}
