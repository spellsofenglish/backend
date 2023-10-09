create table if not exists games
(
    id           uuid not null,
    player_id    uuid not null,
    player_position int default 0,
    index_task      int,
    primary key (id)
);
