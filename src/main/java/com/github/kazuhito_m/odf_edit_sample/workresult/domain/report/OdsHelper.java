package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * OpenDocumentのSpreadsheetに関する、特徴的・頻出なロジックを集めたクラス。。
 */
public class OdsHelper {

    /**
     * 定数「Excelの一秒を計算する定数
     **/
    private static final BigDecimal EXCEL_ONE_SEC = BigDecimal.ONE.divide(BigDecimal.valueOf(86400), 20, RoundingMode.DOWN);

    /**
     * 定数「セルで0時0分でも最初から積まれてる値。」
     */
    private static final BigDecimal DATE_CELL_OFFSET = new BigDecimal("32400000");

    private static final Logger logger = LoggerFactory.getLogger(OdsHelper.class);


    /**
     * 「Javaの日付のミリ秒(Date.getTime()) から、Excelでの日付セルの内部で持ってる数値に変換する。
     *
     * @param dateTimeValue Javaで取り出した日付値。Date.getTime() からの値を期待。
     * @return 変換した値。
     */
    public BigDecimal convJavaDateToExcelDateValue(long dateTimeValue) {
        // まずは引数がBigDecimal化。
        BigDecimal base = BigDecimal.valueOf(dateTimeValue);
        // 次に普通の「秒」へと計算する。
        BigDecimal value = base.add(DATE_CELL_OFFSET).divide(BigDecimal.valueOf(1000));
        //　算出した「秒」を今度は、SpreadSheet側の義判断で。
        return value.multiply(EXCEL_ONE_SEC);
    }


}













