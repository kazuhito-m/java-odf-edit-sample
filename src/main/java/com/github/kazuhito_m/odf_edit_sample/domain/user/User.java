package com.github.kazuhito_m.odf_edit_sample.domain.user;

public class User {
    private final Integer id;
    private final String name;
    private final String caption;
    private final String mailAddress;
    private final String description;
    private final String password;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCaption() {
        return caption;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public String getSummary() {
        return id + ":" + name;
    }

    public User(
            Integer id,
            String name,
            String caption,
            String mailAddress,
            String description,
            String password
    ) {
        this.id = id;
        this.name = name;
        this.caption = caption;
        this.mailAddress = mailAddress;
        this.description = description;
        this.password = password;
    }
}
