CREATE TABLE worker (
    id SERIAL NOT NULL,
    name VARCHAR(255),
    caption VARCHAR(255),
    mailAddress VARCHAR(255),
    description VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE workresult_day (
    id SERIAL NOT NULL,
    user_id INT NOT NULL,
    result_date DATE NOT NULL,
    start_time TIME,
    end_time TIME,
    break_hour FLOAT,
    description VARCHAR(255),
    PRIMARY KEY (id)
);
