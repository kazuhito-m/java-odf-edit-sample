package com.github.kazuhito_m.odf_edit_sample.domain.workresult;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

import static com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils.getYmFmt;

public class WorkResultDay implements Comparable<WorkResultDay> {

    // TODO ドメインに移動したからには、Date,TimeはLocalDateTimeに移行する。

    private final Date resultDate;
    private final Time startTime;
    private final Time endTime;
    private final BigDecimal breakHour;
    private final String description;

    public String convMonth() {
        if (StringUtils.isEmpty(resultDate)) return "";
        return getYmFmt().format(resultDate);
    }

    public static WorkResultDay prototype(Date resultDate) {
        return new WorkResultDay(
                resultDate,
                null,
                null,
                null,
                ""
        );
    }

    public Date getResultDate() {
        return resultDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public BigDecimal getBreakHour() {
        return breakHour;
    }

    public String getDescription() {
        return description;
    }

    public WorkResultDay(
            Date resultDate,
            Time startTime,
            Time endTime,
            BigDecimal breakHour,
            String description) {
        this.resultDate = resultDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakHour = breakHour;
        this.description = description;
    }

    @Override
    public int compareTo(WorkResultDay other) {
        return resultDate.compareTo(other.resultDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof WorkResultDay)
            return compareTo((WorkResultDay) other) == 0;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(resultDate);
    }
}
