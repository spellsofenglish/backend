create table if not exists users
(
    id           uuid,
    username    varchar (30) not null unique,
    email       varchar(50) not null unique ,
    password    varchar (80) not null,
    primary key (id)
);