package com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateUtilsTest {

    @Test
    public void 指定された日の月の最終日を取得できる() throws ParseException {
        // 事前条件
        Date day = DateUtils.toDate("2016/09/28");
        Date expect = DateUtils.toDate("2016/09/30");

        // 実行
        Date actual = DateUtils.getMonthLastDay(day);

        // 検証
        String msg = String.format("Dateが一致しているか確認,actual:%s,expect:%s", actual, expect);
        assertEquals(expect, actual, msg);
        // 時刻系はトランケートされてる。
        msg = String.format("時系列はトランケートされている,actual:%s,expect:09:00:00", actual);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        assertEquals("00:00:00", sdf.format(actual), msg);

    }

    @Test
    public void 指定した範囲の日付のタバを取得できる() throws ParseException {
        // 事前条件
        Date from = DateUtils.toDate("2016/09/01");
        Date to = DateUtils.toDate("2016/09/30");

        // 実行
        List<Date> days = DateUtils.createDateList(from, to);

        // 検証
        assertEquals(30, days.size());
        assertEquals("2016/09/01", toString(days.get(0)));
        assertEquals("2016/09/02", toString(days.get(1)));
        // ...
        assertEquals("2016/09/29", toString(days.get(28)));
        assertEquals("2016/09/30", toString(days.get(29)));
    }

    private String toString(Date day) {
        return DateUtils.toString(day);
    }

}
