create table if not exists player
(
    id       uuid not null unique,
    username text not null unique,
    progress_id uuid

);
alter table player add primary key (id);