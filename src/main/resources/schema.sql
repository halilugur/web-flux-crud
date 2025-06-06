CREATE TABLE IF NOT EXISTS users
(
    id       VARCHAR(255) NOT NULL,
    username VARCHAR(255),
    email    VARCHAR(255),
    password VARCHAR(512),
    role     VARCHAR(255),
    enabled  BOOLEAN,
    created  TIMESTAMP,
    updated  TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS roles
(
    id      VARCHAR(255) NOT NULL,
    name    VARCHAR(255),
    created TIMESTAMP,
    updated TIMESTAMP,
    PRIMARY KEY (id)
);