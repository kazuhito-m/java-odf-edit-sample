package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource;

import com.github.kazuhito_m.odf_edit_sample.domain.report.SpreadSheetReportMaker;
import com.github.kazuhito_m.odf_edit_sample.domain.report.SpreadSheetRepository;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class SpreadSheetDataSource implements SpreadSheetRepository {

    @Override
    public ResponseEntity<byte[]> makeDownloadEntryForOds(SpreadSheetReportMaker reportMaker) throws IOException {
        final File createdOdsFile = makeReport(reportMaker);
        return makeResponseEntity(reportMaker, createdOdsFile);

    }

    @Override
    public File makeReport(SpreadSheetReportMaker reportMaker) throws IOException {
        // テンプレートファイル取得
        final File templateFile = new File(reportMaker.getClass().getResource(reportMaker.getTemplateFileName()).getPath());
        final SpreadSheet ods = SpreadSheet.createFromFile(templateFile);

        reportMaker.writeContent(ods);

        // 一時ファイル作成
        final File work = File.createTempFile(reportMaker.getTemplateFileName(), ".ods");
        // 一時ファイルに書き込み。
        ods.saveAs(work);
        // 完成品ファイルを返す。
        return work;
    }

    private ResponseEntity<byte[]> makeResponseEntity(SpreadSheetReportMaker reportMaker, File createdOdsFile) throws IOException {
        // ファイルからバイナリ(Byte列)を取得。
        final FileSystem fs = FileSystems.getDefault();
        final Path path = fs.getPath(createdOdsFile.getCanonicalPath());
        byte[] contents = Files.readAllBytes(path);
        // ダウンロード用オブジェクトを作成。
        final HttpHeaders h = new HttpHeaders();
        h.add("Content-Type", "application/vnd.oasis.opendocument.spreadsheet;");
        h.setContentDispositionFormData("filename", reportMaker.getReportDlName() + ".ods");
        return new ResponseEntity<>(contents, h, HttpStatus.OK);
    }

}
