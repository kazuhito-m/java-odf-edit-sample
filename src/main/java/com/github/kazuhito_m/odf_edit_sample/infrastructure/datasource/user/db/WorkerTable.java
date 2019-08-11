package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user.db;

import com.github.kazuhito_m.odf_edit_sample.domain.user.User;
import org.seasar.doma.*;

@Entity
@Table(name = "worker")
public class WorkerTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String caption;
    String mailAddress;
    String description;
    String password;

    public User toDomain() {
        return new User(
                id,
                name,
                caption,
                mailAddress,
                description,
                password);
    }
}
