package com.github.kazuhito_m.odf_edit_sample.domain.workresult;

import com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils;

import java.util.*;
import java.util.stream.Stream;

/**
 * 「勤怠履歴」を司るドメインクラス。
 */
public class WorkResults implements Iterable<WorkResultRow> {

    public static final WorkResults EMPTY = new WorkResults(Collections.emptyList());

    private final List<WorkResultRow> rows;

    public static Map<Date, WorkResultRow> createBlankMapBy(Date from, Date to) {
        Map<Date, WorkResultRow> result = new LinkedHashMap<>();
        int index = 1;
        List<Date> dates = DateUtils.createDateList(from, to);
        for (Date date : dates) {
            WorkResultDay day = WorkResultDay.prototype(date);
            WorkResultRow row = new WorkResultRow(index++, day);
            result.put(date, row);
        }
        return result;
    }

    public WorkResultRow firstRow() {
        return rows.get(0);
    }

    public Stream<WorkResultRow> stream() {
        return rows.stream();
    }

    @Override
    public Iterator<WorkResultRow> iterator() {
        return rows.iterator();
    }

    public WorkResults(List<WorkResultRow> rows) {
        this.rows = rows;
    }

}
