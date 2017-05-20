package com.github.kazuhito_m.odf_edit_sample.presentation.view.workresult;

import java.util.List;

public class ConditionForm {
    private String userCaption;
    private List<String> months;
    private String month;

    public String getUserCaption() {
        return userCaption;
    }

    public List<String> getMonths() {
        return months;
    }

    public String getMonth() {
        return month;
    }

    public ConditionForm(String userCaption, List<String> months, String month) {
        this.userCaption = userCaption;
        this.months = months;
        this.month = month;
    }
}
