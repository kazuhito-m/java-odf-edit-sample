package com.github.kazuhito_m.odf_edit_sample.domain.workresult;

public class WorkResultRow {

    private final Integer lineNo;
    private final WorkResultDay day;

    public Integer getLineNo() {
        return lineNo;
    }

    public WorkResultDay getDay() {
        return day;
    }

    public WorkResultRow(int lineNo, WorkResultDay day) {
        this.lineNo = lineNo;
        this.day = day;
    }

}
