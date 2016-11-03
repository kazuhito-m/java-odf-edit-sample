package com.github.kazuhito_m.odf_edit_sample.fw.util;

import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;

/**
 * OpenDocumentのSpreadsheet扱う際の「定番操作」の集約を責務としたユティリティクラス。
 * <p>
 * なんとなく「直感的なソース書いたらそのとおりならない！」
 * という癖のような部分が見えたので、このクラスを設けた。
 */
public class OdsUtils {

    /**
     * 定数「Excelの一秒を計算する定数
     **/
    private static final BigDecimal EXCEL_ONE_SEC = BigDecimal.ONE.divide(BigDecimal.valueOf(86400), 20, RoundingMode.DOWN);

    /**
     * 定数「セルで0時0分でも最初から積まれてる値。」
     */
    private static final BigDecimal DATE_CELL_OFFSET = new BigDecimal("32400000");

    private static final Logger logger = LoggerFactory.getLogger(OdsUtils.class);


    /**
     * 引数に指定されたODSのセルの計算式を再計算する。
     *
     * @param cell ODSのセルオブジェクト。
     */
    public static void recalc(MutableCell<SpreadSheet> cell) {
        cell.setValue(cell.getValue() + " ");   // 再計算の代わりに値を再設定してみる。
    }


    /**
     * 引数に指定されたODSのセルに、指定された値を「型」を考慮し設定する。
     *
     * @param cell  ODSのセルオブジェクト。
     * @param value 設定したい値。
     */
    public static void setValue(MutableCell cell, Object value) {
        if (value == null) {
            // null なら 値をクリア。
            cell.clearValue();
            cell.setValue("", ODValueType.STRING, true, true);
        } else {
            // 時刻(Time型)だけは、特殊扱い。数値化した後にセット。
            if (value instanceof Time) {
                // セルにTime型の値を「時刻書式そのままに」時刻値として設定。
                Time time = (Time) value;
                cell.setValue(convJavaDateToExcelDateValue(time.getTime()), ODValueType.FLOAT, false, true);
            } else {
                // セルにTime型の値を「時刻書式そのままに」時刻値として設定。
                cell.setValue(value);
            }
        }
    }

    /**
     * 「Javaの日付のミリ秒(Date.getTime()) から、Excelでの日付セルの内部で持ってる数値に変換する。
     *
     * @param dateTimeValue Javaで取り出した日付値。Date.getTime() からの値を期待。
     * @return 変換した値。
     */
    public static double convJavaDateToExcelDateValue(long dateTimeValue) {
        // まずは引数がBigDecimal化。
        BigDecimal base = BigDecimal.valueOf(dateTimeValue);
        // 次に普通の「秒」へと計算する。
        BigDecimal value = base.add(DATE_CELL_OFFSET).divide(BigDecimal.valueOf(1000));
        //　算出した「秒」を今度は、SpreadSheet側の義判断で。
        BigDecimal result = value.multiply(EXCEL_ONE_SEC);
        // お疲れ様でした。終了。
        return result.doubleValue();
    }


}
