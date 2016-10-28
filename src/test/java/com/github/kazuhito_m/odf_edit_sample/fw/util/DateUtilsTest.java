package com.github.kazuhito_m.odf_edit_sample.fw.util;

import org.junit.Test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DateUtilsTest {

    @Test
    public void 指定された日の月の最終日を取得できる() throws ParseException {
        // 事前条件
        Date day = DateUtils.toSqlDate("2016/09/28");
        Date expect = DateUtils.toSqlDate("2016/09/30");

        // 実行
        Date actual = DateUtils.getMonthLastDay(day);

        // 検証
        String msg = String.format("Dateが一致しているか確認,actual:%s,expect:%s", actual, expect);
        assertThat(msg, actual, is(expect));
        // 時刻系はトランケートされてる。
        msg = String.format("時系列はトランケートされている,actual:%s,expect:09:00:00", actual);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        assertThat(sdf.format(actual), is("09:00:00"));

    }

}
