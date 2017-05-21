package com.github.kazuhito_m.odf_edit_sample.domain.workresult;

import com.github.kazuhito_m.odf_edit_sample.domain.user.User;

import java.util.List;

public interface WorkResultRepository {

    /**
     * 引数に指定された「年/月」の文字列から、当該月の勤怠データを取得する。<p/>
     * <p>
     * データが無い日には「空オブジェクト」を配して「しっかり一月分のリスト(30日前後）」として出す。
     *
     * @param month 取得したい「年/月」の文字列。
     * @return 当該データ。
     */
    WorkResults findWorkResultBy(User user, String month);

    List<WorkResultDay> findWorkResultDaysBy(User user);

}
