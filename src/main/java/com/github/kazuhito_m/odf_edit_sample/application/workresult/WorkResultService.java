package com.github.kazuhito_m.odf_edit_sample.application.workresult;

import com.github.kazuhito_m.odf_edit_sample.domain.report.SpreadSheetReportMaker;
import com.github.kazuhito_m.odf_edit_sample.domain.report.SpreadSheetRepository;
import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import com.github.kazuhito_m.odf_edit_sample.domain.user.UserRepository;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultDay;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResultRepository;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.WorkResults;
import com.github.kazuhito_m.odf_edit_sample.domain.workresult.report.WorkResultReportMaker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class WorkResultService {

    private final WorkResultRepository workResultRepository;
    private final UserRepository userRepository;
    private final SpreadSheetRepository spreadSheetRepository;

    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    /**
     * 現在のカレントユーザから「勤怠履歴のある月」の文字列のリストを取得する。
     *
     * @return "yyyy/MM"形式の文字列を日付降順に並べたリスト。
     */
    public List<String> getMonths() {
        User currentUser = getCurrentUser();
        List<WorkResultDay> allDays = workResultRepository.findWorkResultDaysBy(currentUser);
        List<String> months = allDays.stream()
                .map(d -> d.convMonth())
                .distinct()
                .collect(toList());
        Collections.reverse(months);    // これをStreamにできないもんか
        months.forEach(System.out::println);
        return months;
    }

    /**
     * <p>
     * 引数に指定された「年/月」の文字列から、当該月の勤怠データを取得する。
     * <p/>
     * データが無い日には「空オブジェクト」を配して「しっかり一月分のリスト(30日前後）」として出す。
     *
     * @param month 取得したい「年/月」の文字列。
     * @return 当該データ。
     */
    public WorkResults findWorkResultBy(String month) {
        User user = userRepository.getCurrentUser();
        return workResultRepository.findWorkResultBy(user, month);
    }

    public ResponseEntity<byte[]> makeDlEntityWorkResultReport(String month) throws IOException {
        // ファイルを作成。
        SpreadSheetReportMaker reportMaker = makeWorkResultReportMaker(month);
        return spreadSheetRepository.makeDownloadEntryForOds(reportMaker);
    }

    private SpreadSheetReportMaker makeWorkResultReportMaker(String month) throws IOException {
        User user = userRepository.getCurrentUser();
        // 画面と同じく、「月条件」でリストW取得する。」
        WorkResults rows = workResultRepository.findWorkResultBy(user, month);
        // 印刷専門家にファイル作成を依頼。
        return new WorkResultReportMaker(user, month, rows);
    }

    public WorkResultService(WorkResultRepository workResultRepository, UserRepository userRepository, SpreadSheetRepository spreadSheetRepository) {
        this.workResultRepository = workResultRepository;
        this.userRepository = userRepository;
        this.spreadSheetRepository = spreadSheetRepository;
    }
}
