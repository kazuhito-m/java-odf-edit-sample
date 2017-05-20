package com.github.kazuhito_m.odf_edit_sample.domain.workresult;

import com.github.kazuhito_m.odf_edit_sample.domain.workresult.report.WorkresultReportMaker;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user.dao.UserDao;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user.entity.User;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.workresult.dao.WorkresultDayDao;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.workresult.entity.WorkresultDay;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils;
import com.github.kazuhito_m.odf_edit_sample.presentation.view.workresult.WorkresultRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils.getMonthLastDay;
import static com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils.getYmFmt;
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

    public Workresult() {
    }

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

            // 9時間ズレ的なおかしなことになるので、日付整え処理。
            timeTruncate(srcs);

            // 空っぽの「1日〜月末」までの表示行を作成。
            Map<Date, WorkresultRow> blankRows = createBlankMapBy(firstDay, lastDay);

            // 日付で「キー当て」にいって、空っぽのものにDBの値を複写する。
            srcs.stream().forEach(d -> blankRows.get(d.resultDate).reflect(d));

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


    protected void timeTruncate(List<WorkresultDay> srcs) {
        srcs.stream().forEach(i -> i.resultDate = DateUtils.timeTruncate(i.resultDate));
    }


    protected Map<Date, WorkresultRow> createBlankMapBy(Date from, Date to) {
        Map<Date, WorkresultRow> result = new LinkedHashMap<>();
        index = 1;
        DateUtils.createDateList(from, to).stream()
                .map(o -> new WorkresultRow(index++, o))
                .forEach(row -> result.put(row.resultDate, row));
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


    private String convMonth(WorkresultDay day) {
        if (!StringUtils.isEmpty(day.resultDate)) {
            return getYmFmt().format(day.resultDate);
        }
        return "";
    }

}
