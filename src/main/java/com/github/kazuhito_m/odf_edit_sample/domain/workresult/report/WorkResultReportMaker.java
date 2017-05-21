package com.github.kazuhito_m.odf_edit_sample.domain.workresult.report;

import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResults;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultDay;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultRow;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.OdsUtils.recalc;
import static com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.OdsUtils.setValue;

/**
 * 印刷用「作業実績表」を作成する責務のクラス。
 */
public class WorkResultReportMaker {

    // TODO 保存部分はインフラ、値セット部分はドメイン、という分け。

    private static final Logger logger = LoggerFactory.getLogger(WorkResultReportMaker.class);

    /**
     * データの開始行。
     */
    private static final int DATA_START_ROW = 11;

    public File makeReport(User user, WorkResults rows) throws IOException {
        // テンプレートファイル取得
        final SpreadSheet ods = SpreadSheet.createFromFile(getOdsTemplateFile());
        final Sheet sheet = ods.getSheet(0);

        // 共通のものは、一件目をサンプリングしてやってもらおう。
        final WorkResultRow firstRow = rows.firstRow();

        setValue(sheet.getCellAt("I2"), firstRow.getDay().getResultDate());
        setValue(sheet.getCellAt("I8"), user.getName());

        int i = DATA_START_ROW; // Cellの指定はXもYも同じで、０オリジンみたい。
        for (WorkResultRow row : rows) {
            final WorkResultDay day = row.getDay();
            // 値代入
            setValue(sheet.getCellAt(1, i), day.getResultDate());
            setValue(sheet.getCellAt(3, i), day.getStartTime());
            setValue(sheet.getCellAt(4, i), day.getEndTime());
            setValue(sheet.getCellAt(5, i), day.getBreakHour());
            setValue(sheet.getCellAt(8, i), day.getDescription());
            if (day.getEndTime() != null) {
                recalc(sheet.getCellAt(6, i));
            }
            i++;
        }

        // 日付と合計行整え
        setValue(sheet.getCellAt("B12"), firstRow.getDay().getResultDate());
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

    public String makeWorkresultReportDlName(String month) {
        return "workresultReport" + month.replaceAll("\\/", "") + ".ods";
    }

    private File getOdsTemplateFile() {
        return new File(this.getClass().getResource("workresultTemplate.ods").getPath());
    }

}
