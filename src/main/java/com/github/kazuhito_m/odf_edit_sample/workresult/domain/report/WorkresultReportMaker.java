package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;

import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.workresult.view.WorkresultRow;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 印刷用「作業実績表」を作成する責務のクラス。
 */
public class WorkresultReportMaker {

    private static final Logger logger = LoggerFactory.getLogger(WorkresultReportMaker.class);

    public File makeReport(User user, List<WorkresultRow> rows) throws IOException {
        // 一時ファイル作成
        // テンプレートファイル取得
        File template = getWorkresultTemplateFile();
        final Sheet sheet = SpreadSheet.createFromFile(template).getSheet(0);

        WorkresultRow firstRow = rows.stream().findFirst().get();


        sheet.getCellAt("I2").setValue(firstRow.resultDate);
        sheet.getCellAt("I8").setValue(user.name);


        int i = 11; // Cellの指定はXもYも同じで、０オリジンみたい。
        for (WorkresultRow row : rows) {
            Date d = row.startTime;
            MutableCell mc = sheet.getCellAt(3, i);

            if (row.startTime != null) {
                mc.setValue(new Date(row.startTime.getTime()));
            }

            mc = sheet.getCellAt(4, i);
            if (row.endTime != null) {
                mc.setValue(new Date(row.endTime.getTime()));
            }


//            mc.setValue("お文字列やたらいｋるんかい？");
//            sheet.getCellAt(4, i).setValue(row.startTime);
            i++;
        }


        File work = File.createTempFile("workresultReports", ".ods");
        OOUtils.open(sheet.getSpreadSheet().saveAs(work));

        // 完成品ファイルを返す。
        return work;
    }

    private File getWorkresultTemplateFile() {
        return new File(this.getClass().getResource("workresultTemplate.ods").getPath());
    }

}













