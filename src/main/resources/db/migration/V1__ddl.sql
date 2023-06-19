CREATE TABLE IF NOT EXISTS users
(
    id                  uuid PRIMARY KEY  NOT NULL,
    email               CHARACTER VARYING(100) NOT NULL,
    "password"          CHARACTER VARYING(150) NOT NULL,
    nick_name           CHARACTER VARYING(100),
    results             CHARACTER VARYING(100),
    "role"              CHARACTER VARYING(50)  NOT NULL,
    is_active           BOOLEAN                NOT NULL DEFAULT FALSE,
    is_using_2FA        BOOLEAN                NOT NULL DEFAULT FALSE,
    secret              CHARACTER VARYING(100)
);

CREATE TABLE IF NOT EXISTS token_links
(
    id                  uuid PRIMARY KEY NOT NULL,
    token               CHARACTER VARYING(40) NOT NULL,
    active_time         BIGINT,
    create_time         TIMESTAMP,
    is_active           BOOLEAN DEFAULT FALSE NOT NULL
);
