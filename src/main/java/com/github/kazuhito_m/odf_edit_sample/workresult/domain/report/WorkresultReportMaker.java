package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * 印刷用「作業実績表」を作成する責務のクラス。
 */
public class WorkresultReportMaker {

    public File makeReportFile() throws IOException {
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
