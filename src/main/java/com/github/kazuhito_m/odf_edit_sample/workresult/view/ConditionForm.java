package com.github.kazuhito_m.odf_edit_sample.workresult.view;

import java.util.List;

public class ConditionForm {

    private String userCaption;
    private List<String> months;
    private String month;


    public String getUserCaption() {
        return userCaption;
    }

    public void setUserCaption(String userCaption) {
        this.userCaption = userCaption;
    }

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

}
