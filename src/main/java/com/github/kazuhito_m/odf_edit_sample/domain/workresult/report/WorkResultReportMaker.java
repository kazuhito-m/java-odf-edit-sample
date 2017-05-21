package com.github.kazuhito_m.odf_edit_sample.domain.workresult.report;

import com.github.kazuhito_m.odf_edit_sample.domain.SpreadSheetReportMaker;
import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultDay;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultRow;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResults;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.OdsUtils.recalc;
import static com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.OdsUtils.setValue;

/**
 * 印刷用「作業実績表」を作成する責務のクラス。
 */
public class WorkResultReportMaker implements SpreadSheetReportMaker {

    private static final Logger logger = LoggerFactory.getLogger(WorkResultReportMaker.class);

    private final User user;
    private final String month;
    private final WorkResults rows;

    /**
     * データの開始行。
     */
    private static final int DATA_START_ROW = 11;

    @Override
    public void writeContent(SpreadSheet ods) {

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

    }

    @Override
    public String getReportDlName() {
        return "workresultReport" + month.replaceAll("\\/", "");
    }

    @Override
    public String getTemplateFileName() {
        return "workresultTemplate.ods";
    }

    public WorkResultReportMaker(User user, String month, WorkResults rows) {
        this.user = user;
        this.month = month;
        this.rows = rows;
    }

}
