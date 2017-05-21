package com.github.kazuhito_m.odf_edit_sample.presentation.controller.workresult;

import com.github.kazuhito_m.odf_edit_sample.App;
import com.github.kazuhito_m.odf_edit_sample.application.workresult.WorkResultService;
import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResults;
import com.github.kazuhito_m.odf_edit_sample.presentation.view.workresult.ConditionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class WorkResultController {

    private static final Logger logger = LoggerFactory.getLogger(WorkResultController.class);

    private final WorkResultService service;

    @ModelAttribute("months")
    List<String> months() {
        return service.getMonths();
    }

    @ModelAttribute("workResults")
    WorkResults workResults(@ModelAttribute("form") ConditionForm form,
                            @ModelAttribute("months") List<String> months) {
        if (!months.contains(form.getMonth())) return WorkResults.EMPTY;
        return service.findWorkResultBy(form.getMonth());
    }

    @ModelAttribute("currentUser")
    User currentUser() {
        return service.getCurrentUser();
    }

    @ModelAttribute("appVer")
    String appVer() {
        return App.VERSION;
    }

    @RequestMapping({"/", "/workresult"})
    public String workResult() {
        return "workresult";
    }

    @RequestMapping({"/dlworkresult"})
    public ResponseEntity<byte[]> dlWorkresult(@ModelAttribute("form") ConditionForm form) throws IOException {
        // ファイルを作成。
        String month = form.getMonth();
        return service.makeDlEntityWorkResultReport(month);
    }

    public WorkResultController(WorkResultService service) {
        this.service = service;
    }

}
