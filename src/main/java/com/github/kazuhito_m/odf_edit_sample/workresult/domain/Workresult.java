package com.github.kazuhito_m.odf_edit_sample.workresult.domain;

import com.github.kazuhito_m.odf_edit_sample.fw.util.DateUtils;
import com.github.kazuhito_m.odf_edit_sample.user.dao.UserDao;
import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.workresult.dao.WorkresultDayDao;
import com.github.kazuhito_m.odf_edit_sample.workresult.domain.report.WorkresultReportMaker;
import com.github.kazuhito_m.odf_edit_sample.workresult.entity.WorkresultDay;
import com.github.kazuhito_m.odf_edit_sample.workresult.view.WorkresultRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private WorkresultReportMaker reportMaker = new WorkresultReportMaker();

    private int index;  // TODO あとで対応。

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

    /**
     * 引数に指定された「年/月」の文字列から、当該月の勤怠データを取得する。<p/>
     * <p>
     * データが無い日には「空オブジェクト」を配して「しっかり一月分のリスト(30日前後）」として出す。
     *
     * @param month 取得したい「年/月」の文字列。
     * @return 当該データ。
     */
    public List<WorkresultRow> findWorkresultBy(String month) {
        try {
            Date firstDay = new Date(DateUtils.getYmdFmt().parse(month + "/01").getTime());
            Date lastDay = getMonthLastDay(firstDay);

            // DBから勤怠履歴データ取得。
            List<WorkresultDay> srcs = workresultDayDao.selectByUserAndDay(getCurrentUser().id, firstDay, lastDay);

            // 空っぽの「1日〜月末」までの表示行を作成。
            Map<Date, WorkresultRow> blankRows = createBlankMapBy(firstDay, lastDay);

            // 日付で「キー当て」にいって、空っぽのものにDBの値を複写する。
            srcs.stream().forEach(d -> blankRows.get(d.resultDate).refrect(d));

            // ValuesをListに変換。
            return blankRows.values().stream().collect(toList());

        } catch (ParseException e) {
            return Collections.emptyList();
        }
    }

    public String makeWorkresultReportDlName(String month) {
        return "workresultReport" + month.replaceAll("\\/", "") + ".ods";
    }

    public File makeFileWorkresultReport(String month) throws IOException {

        // 画面と同じく、「月条件」でリストW取得する。」
        List<WorkresultRow> rows = this.findWorkresultBy(month);
        User user = this.getCurrentUser();
        // 印刷専門家にファイル作成を依頼。
        return reportMaker.makeReport(user, rows);
    }


    /**
     * 当月の「年/月」な文字列を返す。
     *
     * @return 作成された文字列。
     */
    public String getNowMonth() {
        return getYmFmt().format(new java.util.Date());
    }

    protected Map<Date, WorkresultRow> createBlankMapBy(Date from, Date to) {
        Map<Date, WorkresultRow> result = new LinkedHashMap<>();
        index = 1;
        createDateList(from, to).stream().map(o -> new WorkresultRow(index++, o)).forEach(row -> result.put(row.resultDate, row));
        return result;
    }

    /**
     * 範囲指定した「日付のタバ」を作成する。
     *
     * @param from 開始日。
     * @param to   終了日。
     * @return 作成した日付のリスト。
     */
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


    // ユティリティメソッド。

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
            return getYmFmt().format(day.resultDate);
        }
        return "";
    }

    private SimpleDateFormat getYmFmt() {
        return new SimpleDateFormat("yyyy/MM");
    }


}
