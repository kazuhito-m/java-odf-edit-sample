package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;

import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.workresult.view.WorkresultRow;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

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
            sheet.getCellAt(3, i).setValue(fixTime(row.startTime));
            sheet.getCellAt(4, i).setValue(fixTime(row.endTime));
            sheet.getCellAt(5, i).setValue(fixNum(row.breakHour));
            sheet.getCellAt(8, i).setValue(fix(row.description));
            i++;
        }

        // 一時ファイル作成
        File work = File.createTempFile("workresultReports", ".ods");
        // 一時ファイルに書き込み。
        sheet.getSpreadSheet().saveAs(work);

        // 完成品ファイルを返す。
        return work;
    }

    /**
     * 時刻型のために出力内容を加工する。
     *
     * @param time
     * @return
     */
    private Object fixTime(Time time) {
        if (StringUtils.isEmpty(time)) {
            return "";
        }
//        return helper.convJavaDateToExcelDateValue(time.getTime());
        return (new SimpleDateFormat("HH:mm")).format(time);
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













