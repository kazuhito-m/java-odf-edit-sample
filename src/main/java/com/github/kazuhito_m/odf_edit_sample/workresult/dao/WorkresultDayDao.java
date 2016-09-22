package com.github.kazuhito_m.odf_edit_sample.workresult.dao;

import com.github.kazuhito_m.odf_edit_sample.workresult.entity.WorkresultDay;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@ConfigAutowireable
@Dao
public interface WorkresultDayDao {

    @Select
    List<WorkresultDay> selectAll();

    /**
     * 勤怠履歴データをユーザと日付で絞り込んで取得する。
     *
     * @param userId ユーザID。
     * @param from 開始日(当日含む)
     * @param to 終了日(当日含む)
     * @return 絞りこまれた履歴データ
     */
    @Select
    List<WorkresultDay> selectByUserAndDay(Integer userId, Date from, Date to);

    @Insert
    @Transactional
    int insert(WorkresultDay day);

}
