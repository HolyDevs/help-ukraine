CREATE TABLE users
(
    username   VARCHAR(128) PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    role       VARCHAR(128) NOT NULL,
    password   VARCHAR(128) NOT NULL
);

CREATE TABLE roles
(
    name VARCHAR(128) PRIMARY KEY
);