package com.github.kazuhito_m.odf_edit_sample.domain.user;

public interface UserRepository {

    /**
     * 表示対象のユーザを取得する。
     *
     * @return ユーザオブジェクト。
     */
    User getCurrentUser();

}
