package com.github.kazuhito_m.odf_edit_sample.workresult.view;

import com.github.kazuhito_m.odf_edit_sample.workresult.domain.Workresult;
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

import java.io.UnsupportedEncodingException;
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
        form.setUserCaption(domain.getUserCaption());
        form.setMonths(months);

        model.addAttribute("form", form);
        model.addAttribute("workresults", workresults);

        return "workresult";
    }

    @RequestMapping({"/dlworkresult"})
    public ResponseEntity<byte[]> dlWorkresult(@ModelAttribute("form") ConditionForm form, Model model) throws UnsupportedEncodingException {

        if (StringUtils.isEmpty(form.getMonth())) {
            form.setMonth(domain.getNowMonth());
        }

        HttpHeaders h = new HttpHeaders();
        h.add("Content-Type", "text/csv; charset=MS932");
        h.setContentDispositionFormData("filename", "hoge.csv");
        return new ResponseEntity<>(form.getMonth().getBytes("UTF-8"), h, HttpStatus.OK);

    }

}
