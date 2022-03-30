CREATE TABLE IF NOT EXISTS users
(
    id         VARCHAR(128) PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    login      VARCHAR(128) NOT NULL
);