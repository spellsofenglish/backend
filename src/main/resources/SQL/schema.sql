CREATE TABLE IF NOT EXISTS users
(
    id                  BIGSERIAL PRIMARY KEY  NOT NULL,
    email               CHARACTER VARYING(100) NOT NULL,
    "password"          CHARACTER VARYING(150) NOT NULL,
    nick_name           CHARACTER VARYING(100),
    results             CHARACTER VARYING(100),
    "role"              CHARACTER VARYING(50)  NOT NULL,
    is_active           BOOLEAN                NOT NULL DEFAULT TRUE,
    is_using_2FA        BOOLEAN                NOT NULL DEFAULT FALSE,
    secret              CHARACTER VARYING(100)
);

CREATE TABLE IF NOT EXISTS token_links
(
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    token               CHARACTER VARYING(40) NOT NULL,
    active_time         BIGINT,
    create_time         TIMESTAMP,
    is_active           BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE IF NOT EXISTS information
(
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    name                CHARACTER VARYING(50),
    class_message       CHARACTER VARYING(60) NOT NULL,
    message             CHARACTER VARYING(1000),
    note                CHARACTER VARYING(1000),
    is_active           BOOLEAN DEFAULT FALSE NOT NULL
);
