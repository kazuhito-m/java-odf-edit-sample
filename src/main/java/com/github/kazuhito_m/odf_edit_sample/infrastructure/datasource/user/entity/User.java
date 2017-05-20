package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String name;
    public String caption;
    public String mailAddress;
    public String description;
    public String password;
}
