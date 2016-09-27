package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;

import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.workresult.view.WorkresultRow;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.xml.datatype.Duration;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static javax.xml.datatype.DatatypeConstants.Field;

/**
 * 印刷用「作業実績表」を作成する責務のクラス。
 */
public class WorkresultReportMaker {

    private static final Logger logger = LoggerFactory.getLogger(WorkresultReportMaker.class);

    private OdsHelper helper = new OdsHelper();

    public File makeReport(User user, List<WorkresultRow> rows) throws IOException {
        // テンプレートファイル取得
        File template = getWorkresultTemplateFile();
        final Sheet sheet = SpreadSheet.createFromFile(template).getSheet(0);

        // 共通ののものば、さんぷりんぐしてやってもらおう。
        WorkresultRow firstRow = rows.stream().findFirst().get();

        sheet.getCellAt("I2").setValue(firstRow.resultDate);
        sheet.getCellAt("I8").setValue(user.name);
        sheet.getCellAt("B12").setValue(firstRow.resultDate);

        int i = 11; // Cellの指定はXもYも同じで、０オリジンみたい。
        for (WorkresultRow row : rows) {


//            MutableCell cell = sheet.getCellAt(9, i);
            MutableCell cell = sheet.getCellAt(3, i);
//            cell.setValue(row.startTime, true);
            if (row.startTime != null) {

//                CellStyle style = sheet.getStyleAt(3, i);

//                ODValueType valueType = cell.getValueType();


//                ODValueType type = ODValueType.forObject(row.startTime);
//                logger.debug(type.toString());


                // 「DurationはTIME型に判定される」ということから、これをsetValue()してみるが、失敗だった。
                Duration d = new Duration() {
                    @Override
                    public int getSign() {
                        return 0;
                    }

                    @Override
                    public Number getField(Field field) {
//                        logger.debug(field.toString());
                        if (5 == field.getId()) {
                            return new BigDecimal("11");
                        } else {
                            return new BigInteger("59");
                        }
                    }


                    @Override
                    public boolean isSet(Field field) {
                        return false;
                    }

                    @Override
                    public Duration add(Duration duration) {
                        return null;
                    }

                    @Override
                    public void addTo(Calendar calendar) {

                    }

                    @Override
                    public Duration multiply(BigDecimal bigDecimal) {
                        return null;
                    }

                    @Override
                    public Duration negate() {
                        return null;
                    }

                    @Override
                    public Duration normalizeWith(Calendar calendar) {
                        return null;
                    }

                    @Override
                    public int compare(Duration duration) {
                        return 0;
                    }

                    @Override
                    public int hashCode() {
                        return 0;
                    }

                    @Override
                    public int getHours() {
                        return 23;
                    }

                    @Override
                    public int getMinutes() {
                        return 59;
                    }

                    @Override
                    public int getSeconds() {
                        return 32;
                    }
                };

//                type = ODValueType.forObject(d);
//                logger.debug(type.toString());


//                        String styleName = cell.getStyleName();

                Calendar c = Calendar.getInstance();
                c.setTimeZone(TimeZone.getTimeZone("UTC"));
//                c.setTime(row.resultDate);
                c.set(Calendar.HOUR, row.startTime.getHours());
                c.set(Calendar.MINUTE, row.startTime.getMinutes());
                c.set(Calendar.SECOND, row.startTime.getSeconds());

                c.set(Calendar.HOUR, 1);
                c.set(Calendar.MINUTE, 30);
                c.set(Calendar.SECOND, 12);


//                cell.setValue(c, true);
//                cell.setValue(c);
//                cell.setValue(0.5 );    // これはダメ
//                cell.setValue(0.5, ODValueType.FLOAT, false, true); // タイプ指定なら「Timeに数値で値を入れる」ことが可能。

//                cell.setValue(fixTime(row.startTime), ODValueType.FLOAT, false, true);

                setTime(cell, row.startTime);


//                cell.getElement().setAttribute("data-style-name", "TIME");


//                cell.setValue(d, true);

//                cell.setStyleName(styleName);

//                logger.debug(cell.getDataStyle().toString());
//                logger.debug(cell.getStyleName());
//                logger.debug(cell.getStyle().toString());
//                System.out.println(cell.getStyle().toString());
            }

            // 値代入系
//            sheet.getCellAt(4, i).setValue(fixTime(row.endTime));
            setTime(sheet.getCellAt(4, i), row.endTime);
            sheet.getCellAt(5, i).setValue(fixNum(row.breakHour));
            sheet.getCellAt(8, i).setValue(fix(row.description));

            // 再計算系
            recalc(sheet.getCellAt(6, i));
            sheet.getCellAt(7, i).setValue(sheet.getCellAt(7, i).getValue());

            i++;
        }

        // 一時ファイル作成
        File work = File.createTempFile("workresultReports", ".ods");
        // 一時ファイルに書き込み。
        sheet.getSpreadSheet().saveAs(work);

        // 完成品ファイルを返す。
        return work;
    }

    private void recalc(MutableCell<SpreadSheet> cell) {
        cell.setValue(cell.getValue() + " ");                // 再計算の代わりに値を再設定してみる。
    }

    /**
     * セルにTime型の値を「時刻書式そのままに」時刻値として設定する。
     *
     * @param cell Spreadsheetの1セル。(時刻型)
     * @param time SQLの時刻型値。
     */
    private void setTime(MutableCell cell, Time time) {
        if (time == null) {
            // null なら ""(から文字)をセット。
            cell.clearValue();
            cell.setValue("", ODValueType.STRING, true, true);
        } else {
            // セルにTime型の値を「時刻書式そのままに」時刻値として設定。
            cell.setValue(helper.convJavaDateToExcelDateValue(time.getTime()), ODValueType.FLOAT, false, true);
        }
    }

    private Object fixNum(BigDecimal value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        return value.doubleValue();
    }

    private Object fix(String value) {
        return value == null ? "" : value;
    }

    private File getWorkresultTemplateFile() {
        return new File(this.getClass().getResource("workresultTemplate.ods").getPath());
    }

}













