package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user;

import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import com.github.kazuhito_m.odf_edit_sample.domain.user.UserRepository;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user.dao.UserDao;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.workresult.dao.WorkResultDayDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDataSource implements UserRepository {

    /**
     * デフォルトのユーザ(後には選べるようにしたいが、今は固定で一人しか選べない)
     */
    private static final Integer DEFAULT_USER_ID = 1;

    private final UserDao userDao;

    /**
     * 表示対象のユーザを取得する。
     *
     * @return ユーザオブジェクト。
     */
    @Override
    public User getCurrentUser() {
        return userDao.selectById(DEFAULT_USER_ID).toDomain();
    }

    public UserDataSource(UserDao userDao, WorkResultDayDao workResultDayDao) {
        this.userDao = userDao;
    }

}
