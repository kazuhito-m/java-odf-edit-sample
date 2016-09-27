package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;

import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.workresult.view.WorkresultRow;
import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.List;

/**
 * 印刷用「作業実績表」を作成する責務のクラス。
 */
public class WorkresultReportMaker {

    private static final Logger logger = LoggerFactory.getLogger(WorkresultReportMaker.class);

    /**
     * データの開始行。
     */
    private static final int DATA_START_ROW = 11;

    private OdsHelper helper = new OdsHelper();

    public File makeReport(User user, List<WorkresultRow> rows) throws IOException {
        // テンプレートファイル取得
        SpreadSheet ods = SpreadSheet.createFromFile(getOdsTemplateFile());
        final Sheet sheet = ods.getSheet(0);

        // 共通のものは、一件目をサンプリングしてやってもらおう。
        WorkresultRow firstRow = rows.get(0);

        setValue(sheet.getCellAt("I2"), firstRow.resultDate);
        setValue(sheet.getCellAt("I8"), user.name);
        setValue(sheet.getCellAt("B12"), firstRow.resultDate);

        int i = DATA_START_ROW; // Cellの指定はXもYも同じで、０オリジンみたい。
        for (WorkresultRow row : rows) {
            // 値代入
            setValue(sheet.getCellAt(3, i), row.startTime);
            setValue(sheet.getCellAt(4, i), row.endTime);
            setValue(sheet.getCellAt(5, i), row.breakHour);
            setValue(sheet.getCellAt(8, i), row.description);
            // 再計算
            if (i > DATA_START_ROW) {
                recalc(sheet.getCellAt(1, i));
            }
            if (row.endTime != null) {
                recalc(sheet.getCellAt(6, i));
            }
            recalc(sheet.getCellAt(7, i));
            recalc(sheet.getCellAt(2, i));
            i++;
        }

        // 一時ファイル作成
        File work = File.createTempFile("workresultReports", ".ods");
        // 一時ファイルに書き込み。
        ods.saveAs(work);

        // 完成品ファイルを返す。
        return work;
    }

    /**
     * 引数に指定されたODSのセルの計算式を再計算する。
     *
     * @param cell ODSのセルオブジェクト。
     */
    private void recalc(MutableCell<SpreadSheet> cell) {
        cell.setValue(cell.getValue() + " ");   // 再計算の代わりに値を再設定してみる。
    }


    /**
     * 引数に指定されたODSのセルに、指定された値を「型」を考慮し設定する。
     *
     * @param cell  ODSのセルオブジェクト。
     * @param value 設定したい値。
     */
    private void setValue(MutableCell cell, Object value) {
        if (value == null) {
            // null なら 値をクリア。
            cell.clearValue();
            cell.setValue("", ODValueType.STRING, true, true);
        } else {
            // 時刻(Time型)だけは、特殊扱い。数値化した後にセット。
            if (value instanceof Time) {
                // セルにTime型の値を「時刻書式そのままに」時刻値として設定。
                Time time = (Time) value;
                cell.setValue(helper.convJavaDateToExcelDateValue(time.getTime()), ODValueType.FLOAT, false, true);
            } else {
                // セルにTime型の値を「時刻書式そのままに」時刻値として設定。
                cell.setValue(value);
            }
        }
    }

    private File getOdsTemplateFile() {
        return new File(this.getClass().getResource("workresultTemplate.ods").getPath());
    }

}
