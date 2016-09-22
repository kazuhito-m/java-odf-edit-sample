CREATE TABLE user (
    id MEDIUMINT  NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    caption VARCHAR(255),
    mailAddress VARCHAR(255),
    description VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY (ID)
);

CREATE TABLE workresult_day (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    user_id NOT NULL
    result_date DATE NOT NULL ,
    start_time TIME,
    end_time TIME,
    break_hour FLOAT,
    description VARCHAR(255),
    PRIMARY KEY (ID)
);
