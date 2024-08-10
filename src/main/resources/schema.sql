CREATE TABLE IF NOT EXISTS users
(
    id       VARCHAR(255) NOT NULL,
    username VARCHAR(255),
    email    VARCHAR(255),
    password VARCHAR(512),
    enabled  BOOLEAN,
    created  DATETIME,
    updated  DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles
(
    id      VARCHAR(255) NOT NULL,
    role    VARCHAR(255),
    created DATETIME,
    updated DATETIME,
    PRIMARY KEY (id)
);