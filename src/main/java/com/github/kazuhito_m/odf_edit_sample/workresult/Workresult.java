package com.github.kazuhito_m.odf_edit_sample.workresult;

import com.github.kazuhito_m.odf_edit_sample.user.dao.UserDao;
import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.workresult.dao.WorkresultDayDao;
import com.github.kazuhito_m.odf_edit_sample.workresult.entity.WorkresultDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
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

    private String convMonth(WorkresultDay day) {
        if (!StringUtils.isEmpty(day.resultDate)) {
            return (new SimpleDateFormat("yyyy/MM")).format(day.resultDate);
        }
        return "";
    }

    /**
     * 表示対象のユーザを取得する。
     *
     * @return ユーザオブジェクト。
     */
    protected User getCurrentUser() {
        return userDao.selectById(DEFAULT_USER_ID);
    }
}
