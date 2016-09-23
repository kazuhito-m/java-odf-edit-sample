package com.github.kazuhito_m.odf_edit_sample.workresult.domain;

import com.github.kazuhito_m.odf_edit_sample.user.dao.UserDao;
import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.workresult.dao.WorkresultDayDao;
import com.github.kazuhito_m.odf_edit_sample.workresult.entity.WorkresultDay;
import com.github.kazuhito_m.odf_edit_sample.workresult.view.WorkresultRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 「勤怠履歴」を司るドメインクラス。
 */
@Component
public class Workresult {

    /**
     * デフォルトのユーザ(後には選べるようにしたいが、今は固定で一人しか選べない)
     */
    private static final Integer DEFAULT_USER_ID = 1;

    @Autowired
    private UserDao userDao;

    @Autowired
    private WorkresultDayDao workresultDayDao;

    public String getUserCaption() {
        User user = getCurrentUser();
        String caption = user.id + ":" + user.name;
        return caption;
    }

    /**
     * 現在のカレントユーザから「勤怠履歴のある月」の文字列のリストを取得する。
     *
     * @return "yyyy/MM"形式の文字列を日付降順に並べたリスト。
     */
    public List<String> getMonths() {
        List<WorkresultDay> allDays = workresultDayDao.selectByUser(getCurrentUser().id);
        List<String> months = allDays.stream().map(i -> convMonth(i)).distinct().collect(toList());
        Collections.reverse(months);    // これをStreamにできないもんか
        return months;
    }

    public List<WorkresultRow> findWorkresultBy(String month) {
        try {
            Date firstDay = new Date(new SimpleDateFormat("yyyy/MM/dd").parse(month + "/01").getTime());
            Date lastDay = getMonthLastDay(firstDay);

            List<WorkresultDay> srcs = workresultDayDao.selectByUserAndDay(getCurrentUser().id, firstDay, lastDay);

            // TODO
            return null;

        } catch (ParseException e) {
            return Collections.emptyList();
        }
    }

    public List<WorkresultRow> createBlankListBy(Date from, Date to) {
        // TODO
        return null;
    }

    protected List<Date> createDateList(Date from, Date to) {
        List<Date> result = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(from);
        Date day;
        do {
            day = new Date(cal.getTimeInMillis());
            result.add(day);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        } while (day.before(to));
        return result;
    }


    /**
     * 表示対象のユーザを取得する。
     *
     * @return ユーザオブジェクト。
     */
    protected User getCurrentUser() {
        return userDao.selectById(DEFAULT_USER_ID);
    }


    /**
     * 指定された日付の月の最終日を取得する。
     *
     * @param day 対象となる日。
     * @return 計算し取得された月末日。
     */
    private Date getMonthLastDay(Date day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return new Date(cal.getTimeInMillis());
    }


    private String convMonth(WorkresultDay day) {
        if (!StringUtils.isEmpty(day.resultDate)) {
            return (new SimpleDateFormat("yyyy/MM")).format(day.resultDate);
        }
        return "";
    }

}
