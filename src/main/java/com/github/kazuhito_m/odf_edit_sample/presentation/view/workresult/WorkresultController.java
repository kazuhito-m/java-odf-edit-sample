package com.github.kazuhito_m.odf_edit_sample.presentation.view.workresult;

import com.github.kazuhito_m.odf_edit_sample.App;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.Workresult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Controller
public class WorkresultController {

    private static final Logger logger = LoggerFactory.getLogger(WorkresultController.class);

    @Autowired
    private Workresult domain;

    @RequestMapping({"/", "/workresult"})
    public String workresult(@ModelAttribute("form") ConditionForm form, Model model) {

        List<String> months = domain.getMonths();

        List<WorkresultRow> workresults = Collections.emptyList();
        if (months.contains(form.getMonth())) {
            workresults = domain.findWorkresultBy(form.getMonth());
        }
        final ConditionForm modifyForm = new ConditionForm(domain.getUserCaption(), months, form.getMonth());

        model.addAttribute("form", modifyForm);
        model.addAttribute("workresults", workresults);
        model.addAttribute("appVer", App.VERSION);

        return "workresult";
    }

    @RequestMapping({"/dlworkresult"})
    public ResponseEntity<byte[]> dlWorkresult(@ModelAttribute("form") ConditionForm form, Model model) throws IOException {

        // 指定年月が不明なら、今月とする。
        ConditionForm modifyForm = form;
        if (StringUtils.isEmpty(form.getMonth())) {
            modifyForm = new ConditionForm(form.getUserCaption(),form.getMonths() , domain.getNowMonth());
        }

        // ファイルを作成。
        String downloadName = domain.makeWorkresultReportDlName(modifyForm.getMonth());
        File file = domain.makeFileWorkresultReport(modifyForm.getMonth());

        return makeDownloadEntryForOds(file, downloadName);

    }

    protected ResponseEntity<byte[]> makeDownloadEntryForOds(File localFile, String downloadName) throws IOException {

        // ファイルからバイナリ(Byte列)を取得。
        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath(localFile.getCanonicalPath());
        byte[] contents = Files.readAllBytes(path);

        // ダウンロード用オブジェクトを作成。
        HttpHeaders h = new HttpHeaders();
        h.add("Content-Type", "application/vnd.oasis.opendocument.spreadsheet;");
        h.setContentDispositionFormData("filename", downloadName);
        return new ResponseEntity<>(contents, h, HttpStatus.OK);

    }

}
