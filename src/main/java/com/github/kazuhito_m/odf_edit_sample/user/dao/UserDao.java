package com.github.kazuhito_m.odf_edit_sample.user.dao;

import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ConfigAutowireable
@Dao
public interface UserDao {
    @Select
    List<User> selectAll();

    @Insert
    @Transactional
    int insert(User User);
}