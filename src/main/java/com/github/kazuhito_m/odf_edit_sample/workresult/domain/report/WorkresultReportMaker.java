package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;

import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.workresult.view.WorkresultRow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * 印刷用「作業実績表」を作成する責務のクラス。
 */
public class WorkresultReportMaker {

    public File makeReport(User user, String month, List<WorkresultRow> rows) throws IOException {
        // 一時ファイル作成
        File work = File.createTempFile("workresultReports", ".ods");
        // テンプレートファイル取得
        File template = getWorkresultTemplateFile();
        // ファイルをコピー
        Files.copy(template.toPath(), work.toPath(), StandardCopyOption.REPLACE_EXISTING);
        // 完成品ファイルを返す。
        return work;
    }

    private File getWorkresultTemplateFile() {
        return new File(this.getClass().getResource("workresultTemplate.ods").getPath());
    }

}













