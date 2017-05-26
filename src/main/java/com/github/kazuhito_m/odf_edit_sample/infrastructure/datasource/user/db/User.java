package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user.db;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String caption;
    String mailAddress;
    String description;
    String password;

    public com.github.kazuhito_m.odf_edit_sample.domain.user.User toDomain() {
        return new com.github.kazuhito_m.odf_edit_sample.domain.user.User(
                id,
                name,
                caption,
                mailAddress,
                description,
                password);
    }

}
