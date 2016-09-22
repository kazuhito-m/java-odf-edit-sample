package com.github.kazuhito_m.odf_edit_sample.workresult.dao;

import com.github.kazuhito_m.odf_edit_sample.workresult.entity.WorkresultDay;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ConfigAutowireable
@Dao
public interface WorkresultDayDao {

    @Select
    List<WorkresultDay> selectAll();

//    List<WorkresultDay> selectByUser(Integer userId);

    @Insert
    @Transactional
    int insert(WorkresultDay day);

}
