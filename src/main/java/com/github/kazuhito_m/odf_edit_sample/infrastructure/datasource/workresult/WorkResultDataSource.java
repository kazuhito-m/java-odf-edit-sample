package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.workresult;

import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultDay;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultRepository;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultRow;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResults;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.workresult.db.WorkResultDayDao;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils.getMonthLastDay;
import static java.util.stream.Collectors.toList;

@Repository
public class WorkResultDataSource implements WorkResultRepository {

    private final WorkResultDayDao workResultDayDao;

    @Override
    public WorkResults findWorkResultBy(User user, String month) {
        try {
            Date firstDay = new Date(DateUtils.getYmdFmt().parse(month + "/01").getTime());
            Date lastDay = getMonthLastDay(firstDay);

            // DBから勤怠履歴データ取得。
            List<WorkResultDay> days = workResultDayDao.selectByUserAndDay(user.getId(), firstDay, lastDay).stream()
                    .map(d -> d.toDomain())
                    .collect(toList());

            // 空っぽの「1日〜月末」までの表示行を作成。
            Map<Date, WorkResultRow> blankRows = WorkResults.createBlankMapBy(firstDay, lastDay);

            // 日付で「キー当て」にいって、空っぽのものにDBの値を複写する。
            List<WorkResultRow> rows = days.stream()
                    .map(d -> {
                        WorkResultRow hitRow = blankRows.get(d.getResultDate());
                        return new WorkResultRow(hitRow.getLineNo(), d);
                    })
                    .collect(toList()); // ValuesをListに変換。
            return new WorkResults(rows);
        } catch (ParseException e) {
            return WorkResults.EMPTY;
        }
    }

    @Override
    public List<WorkResultDay> findWorkResultDaysBy(User user) {
        return workResultDayDao.selectByUser(user.getId()).stream()
                .map(d -> d.toDomain())
                .collect(toList());
    }

    public WorkResultDataSource(WorkResultDayDao workResultDayDao) {
        this.workResultDayDao = workResultDayDao;
    }

}
