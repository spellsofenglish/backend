create table if not exists games
(
    id           uuid ,
    player_id    uuid not null,
    player_position int default 0,
    index_task      int,
    primary key (id)
);
