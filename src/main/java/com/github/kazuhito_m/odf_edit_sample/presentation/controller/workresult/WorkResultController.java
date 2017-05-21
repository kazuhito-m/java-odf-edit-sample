package com.github.kazuhito_m.odf_edit_sample.presentation.controller.workresult;

import com.github.kazuhito_m.odf_edit_sample.App;
import com.github.kazuhito_m.odf_edit_sample.application.workresult.WorkResultService;
import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResults;
import com.github.kazuhito_m.odf_edit_sample.presentation.view.workresult.ConditionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;

@Controller
public class WorkResultController {

    private static final Logger logger = LoggerFactory.getLogger(WorkResultController.class);

    private final WorkResultService service;

    @RequestMapping({"/", "/workresult"})
    public String workResult(@ModelAttribute("form") ConditionForm form, Model model) {

        List<String> months = service.getMonths();

        WorkResults workResults = WorkResults.EMPTY;
        if (months.contains(form.getMonth())) {
            workResults = service.findWorkResultBy(form.getMonth());
        }
        User currentUser = service.getCurrentUser();
        final ConditionForm modifyForm = new ConditionForm(currentUser.getSummary(), months, form.getMonth());

        model.addAttribute("form", modifyForm);
        model.addAttribute("workresults", workResults);
        model.addAttribute("appVer", App.VERSION);

        return "workresult";
    }

    @RequestMapping({"/dlworkresult"})
    public ResponseEntity<byte[]> dlWorkresult(@ModelAttribute("form") ConditionForm form, Model model) throws IOException {

        // 指定年月が不明なら、今月とする。
        ConditionForm modifyForm = form;
        if (StringUtils.isEmpty(form.getMonth())) {
            modifyForm = new ConditionForm(form.getUserCaption(), form.getMonths(), service.getNowMonth());
        }

        // ファイルを作成。
        String downloadName = service.makeWorkResultReportDlName(modifyForm.getMonth());
        File file = service.makeFileWorkResultReport(modifyForm.getMonth());

        return makeDownloadEntryForOds(file, downloadName);

    }

    private ResponseEntity<byte[]> makeDownloadEntryForOds(File localFile, String downloadName) throws IOException {

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

    public WorkResultController(WorkResultService service) {
        this.service = service;
    }
}
