package com.github.kazuhito_m.odf_edit_sample.workresult.view;

import lombok.Data;

import java.util.List;

@Data
public class ConditionForm {
    private String userCaption;
    private List<String> months;
    private String month;
}
