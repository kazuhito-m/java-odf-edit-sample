package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;

import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.workresult.view.WorkresultRow;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.github.kazuhito_m.odf_edit_sample.fw.util.OdsUtils.recalc;
import static com.github.kazuhito_m.odf_edit_sample.fw.util.OdsUtils.setValue;

/**
 * 印刷用「作業実績表」を作成する責務のクラス。
 */
public class WorkresultReportMaker {

    private static final Logger logger = LoggerFactory.getLogger(WorkresultReportMaker.class);

    /**
     * データの開始行。
     */
    private static final int DATA_START_ROW = 11;

    public File makeReport(User user, List<WorkresultRow> rows) throws IOException {
        // テンプレートファイル取得
        SpreadSheet ods = SpreadSheet.createFromFile(getOdsTemplateFile());
        final Sheet sheet = ods.getSheet(0);

        // 共通のものは、一件目をサンプリングしてやってもらおう。
        WorkresultRow firstRow = rows.get(0);

        setValue(sheet.getCellAt("I2"), firstRow.resultDate);
        setValue(sheet.getCellAt("I8"), user.name);

        int i = DATA_START_ROW; // Cellの指定はXもYも同じで、０オリジンみたい。
        for (WorkresultRow row : rows) {
            // 値代入
            setValue(sheet.getCellAt(1, i), row.resultDate);
            setValue(sheet.getCellAt(3, i), row.startTime);
            setValue(sheet.getCellAt(4, i), row.endTime);
            setValue(sheet.getCellAt(5, i), row.breakHour);
            setValue(sheet.getCellAt(8, i), row.description);
            if (row.endTime != null) {
                recalc(sheet.getCellAt(6, i));
            }
            i++;
        }

        // 日付と合計行整え
        setValue(sheet.getCellAt("B12"), firstRow.resultDate);
        for (int j = DATA_START_ROW; j < DATA_START_ROW + 31; j++) {
            recalc(sheet.getCellAt(2, i));
            recalc(sheet.getCellAt(7, j));
        }
        recalc(sheet.getCellAt(7, 42)); // 合計行

        // 一時ファイル作成
        File work = File.createTempFile("workresultReports", ".ods");
        // 一時ファイルに書き込み。
        ods.saveAs(work);

        // 完成品ファイルを返す。
        return work;
    }

    private File getOdsTemplateFile() {
        return new File(this.getClass().getResource("workresultTemplate.ods").getPath());
    }

}
