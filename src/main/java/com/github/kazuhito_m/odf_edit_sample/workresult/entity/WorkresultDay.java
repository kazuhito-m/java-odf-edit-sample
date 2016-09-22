package com.github.kazuhito_m.odf_edit_sample.workresult.entity;

import org.seasar.doma.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Entity
public class WorkresultDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "user_id")
    public Integer userId;

    @Column(name = "result_date")
    public Date resultDate;

    @Column(name = "start_time")
    public Time startTime;

    @Column(name = "end_time")
    public Time endTime;

    @Column(name = "break_hour")
    public BigDecimal breakHour;

    @Column(name = "description")
    public String description;

}
