package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user;

import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import com.github.kazuhito_m.odf_edit_sample.domain.user.UserRepository;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user.db.WorkerDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDataSource implements UserRepository {

    /**
     * デフォルトのユーザ(後には選べるようにしたいが、今は固定で一人しか選べない)
     */
    private static final Integer DEFAULT_USER_ID = 1;

    private final WorkerDao workerDao;

    /**
     * 表示対象のユーザを取得する。
     *
     * @return ユーザオブジェクト。
     */
    @Override
    public User getCurrentUser() {
        return workerDao.selectById(DEFAULT_USER_ID).toDomain();
    }

    public UserDataSource(WorkerDao workerDao) {
        this.workerDao = workerDao;
    }

}
