package com.github.kazuhito_m.odf_edit_sample.domain.user;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    /**
     * 表示対象のユーザを取得する。
     *
     * @return ユーザオブジェクト。
     */
    User getCurrentUser();

}
