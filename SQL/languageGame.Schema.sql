/* DROP TABLE IF EXISTS users; */

CREATE TABLE IF NOT EXISTS users
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email               CHARACTER VARYING(100) NOT NULL,
    password            CHARACTER VARYING(150) NOT NULL,
    first_name          CHARACTER VARYING(100) NOT NULL,
    last_name           CHARACTER VARYING(100) NOT NULL,
    role                CHARACTER VARYING(50)  NOT NULL,
    is_active           BOOLEAN                NOT NULL DEFAULT TRUE
);