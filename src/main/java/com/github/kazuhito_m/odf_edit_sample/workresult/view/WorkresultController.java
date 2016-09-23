package com.github.kazuhito_m.odf_edit_sample.workresult.view;

import com.github.kazuhito_m.odf_edit_sample.user.dao.UserDao;
import com.github.kazuhito_m.odf_edit_sample.workresult.Workresult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class WorkresultController {

    @Autowired
    private Workresult domain;

    @Autowired
    private UserDao userDao;

    @RequestMapping({"/", "/workresult"})
    public String workresult(@ModelAttribute("form") ConditionForm form, Model model) {


        List<String> months = domain.getMonths();

        form.setUserCaption(domain.getUserCaption());
        form.setMonths(months);

        model.addAttribute("form", form);
        return "workresult";
    }

}
