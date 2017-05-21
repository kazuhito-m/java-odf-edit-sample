package com.github.kazuhito_m.odf_edit_sample.application.workresult;

import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import com.github.kazuhito_m.odf_edit_sample.domain.user.UserRepository;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultDay;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultRepository;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResults;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.report.WorkResultReportMaker;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util.DateUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class WorkResultService {

    private final WorkResultRepository workResultRepository;
    private final UserRepository userRepository;

    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    /**
     * 現在のカレントユーザから「勤怠履歴のある月」の文字列のリストを取得する。
     *
     * @return "yyyy/MM"形式の文字列を日付降順に並べたリスト。
     */
    public List<String> getMonths() {
        User currentUser = userRepository.getCurrentUser();
        List<WorkResultDay> allDays = workResultRepository.findWorkResultDaysBy(currentUser);
        List<String> months = allDays.stream()
                .map(d -> d.convMonth())
                .distinct()
                .collect(toList());
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
    public WorkResults findWorkResultBy(String month) {
        User user = userRepository.getCurrentUser();
        return workResultRepository.findWorkResultBy(user, month);
    }

    /**
     * 当月の「年/月」な文字列を返す。
     *
     * @return 作成された文字列。
     */
    public String getNowMonth() {
        return DateUtils.getYmFmt().format(new java.util.Date());
    }

    public File makeFileWorkResultReport(String month) throws IOException {
        User user = userRepository.getCurrentUser();
        // 画面と同じく、「月条件」でリストW取得する。」
        WorkResults rows = workResultRepository.findWorkResultBy(user, month);
        // 印刷専門家にファイル作成を依頼。
        WorkResultReportMaker reportMaker = new WorkResultReportMaker();
        return reportMaker.makeReport(user, rows);
    }

    public String makeWorkResultReportDlName(String month) {
        WorkResultReportMaker reportMaker = new WorkResultReportMaker();
        return reportMaker.makeWorkresultReportDlName(month);
    }

    public WorkResultService(WorkResultRepository workResultRepository, UserRepository userRepository) {
        this.workResultRepository = workResultRepository;
        this.userRepository = userRepository;
    }
}
