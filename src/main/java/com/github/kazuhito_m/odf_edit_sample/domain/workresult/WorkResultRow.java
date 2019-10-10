package com.github.kazuhito_m.odf_edit_sample.domain.workresult;

import java.util.Objects;

public class WorkResultRow implements Comparable<WorkResultRow> {
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

    @Override
    public int compareTo(WorkResultRow other) {
        return day.compareTo(other.day);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof WorkResultRow)
            return compareTo((WorkResultRow) other) == 0;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(day);
    }
}
