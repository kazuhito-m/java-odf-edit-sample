package com.github.kazuhito_m.odf_edit_sample.fw.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by kazuhito on 16/09/24.
 */
public class DateUtils {

    public static SimpleDateFormat getYmdFmt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf;
    }

    public static SimpleDateFormat getYmFmt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf;
    }


    public static String toString(Date day) {
        return getYmdFmt().format(day);
    }

    public static Date toSqlDate(String day) throws ParseException {
        return new Date(getYmdFmt().parse(day).getTime());
    }

}
