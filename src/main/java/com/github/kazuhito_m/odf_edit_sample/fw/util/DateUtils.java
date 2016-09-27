package com.github.kazuhito_m.odf_edit_sample.fw.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class DateUtils {

    private static TimeZone TIMEZONE = TimeZone.getTimeZone("UTC");


    public static SimpleDateFormat getYmdFmt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setTimeZone(TIMEZONE);
        return sdf;
    }

    public static SimpleDateFormat getYmFmt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        sdf.setTimeZone(TIMEZONE);
        return sdf;
    }


    public static String toString(Date day) {
        return getYmdFmt().format(day);
    }

    public static Date toSqlDate(String day) throws ParseException {
        return new Date(getYmdFmt().parse(day).getTime());
    }

    /**
     * SQL.Dateの部分で「時刻が在る場合にそれを削除する」ルーティン。
     *
     * @param day 対象日付。
     * @return 加工済みの日付。
     */
    public static Date timeTruncate(Date day) {
        Calendar cal = getCalender(day);
        cal.set(Calendar.HOUR, 0);
        cal.add(Calendar.MINUTE, 0);
        cal.add(Calendar.SECOND, 0);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 指定された日付の月の最終日を取得する。
     *
     * @param day 対象となる日。
     * @return 計算し取得された月末日。
     */
    public static Date getMonthLastDay(Date day) {
        Calendar cal = getCalender(day);
        // 時刻系は０クリア
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        // ツイタチにして、一月足して、一日引く
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 範囲指定した「日付のタバ」を作成する。
     *
     * @param from 開始日。
     * @param to   終了日。
     * @return 作成した日付のリスト。
     */
    public static List<Date> createDateList(Date from, Date to) {
        List<Date> result = new ArrayList<>();
        Calendar cal = getCalender(from);
        Date day;
        do {
            day = new Date(cal.getTimeInMillis());
            result.add(day);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        } while (day.before(to));
        return result;
    }

    private static Calendar getCalender(Date day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.setTimeZone(TIMEZONE);
        return cal;
    }

}
