package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user.db;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ConfigAutowireable
@Dao
public interface WorkerDao {

    @Select
    List<WorkerTable> selectAll();

    @Select
    WorkerTable selectById(Integer userId);

    @Insert
    @Transactional
    int insert(WorkerTable user);

}
