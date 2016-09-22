package com.github.kazuhito_m.odf_edit_sample.workresult;

import com.github.kazuhito_m.odf_edit_sample.user.dao.UserDao;
import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public String getUserCaption() {
        User user = userDao.selectById(DEFAULT_USER_ID);
        String caption = user.id + ":" + user.name;
        return caption;
    }

}
