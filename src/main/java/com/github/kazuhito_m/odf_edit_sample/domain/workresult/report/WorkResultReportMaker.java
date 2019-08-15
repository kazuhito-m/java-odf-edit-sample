package com.github.kazuhito_m.odf_edit_sample.domain.workresult.report;

import com.github.kazuhito_m.odf_edit_sample.domain.report.SpreadSheetReportMaker;
import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultDay;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultRow;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResults;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.util.Date;

import static com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.OdsUtils.recalc;
import static com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.OdsUtils.setValue;

/**
 * 印刷用「作業実績表」を作成する責務のクラス。
 */
public class WorkResultReportMaker implements SpreadSheetReportMaker {
    /**
     * データの開始行。
     */
    private static final int DATA_START_ROW = 11;

    private final User user;
    private final String month;
    private final WorkResults rows;

    @Override
    public void writeContent(SpreadSheet ods) {
        final Sheet sheet = ods.getSheet(0);

        // 共通のものは、一件目をサンプリングしてやってもらおう。
        final WorkResultRow firstRow = rows.firstRow();
        Date firstDay = fixDate(firstRow.getDay().getResultDate());

        setValue(sheet.getCellAt("I2"), firstDay);
        setValue(sheet.getCellAt("I8"), user.getName());

        int i = DATA_START_ROW; // Cellの指定はXもYも同じで、０オリジンみたい。
        for (WorkResultRow row : rows) {
            final WorkResultDay day = row.getDay();
            // 値代入
            setValue(sheet.getCellAt(1, i), fixDate(day.getResultDate()));
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
        setValue(sheet.getCellAt("B12"), firstDay);
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

    // FIXME 9時間ズレ的なおかしなことになるので、日付整え処理。
    private Date fixDate(Date date) {
        return DateUtils.addHour(date, 9);
    }

    public WorkResultReportMaker(User user, String month, WorkResults rows) {
        this.user = user;
        this.month = month;
        this.rows = rows;
    }
}
