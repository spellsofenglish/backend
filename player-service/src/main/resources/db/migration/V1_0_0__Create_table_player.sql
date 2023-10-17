create table if not exists progress
(
    id           uuid ,
    progress     numeric(4, 1),
    total_points int4,
    game_level   int4,
    primary key (id)
);

create table if not exists player
(
    id          uuid ,
    progress_id uuid not null references progress (id) on delete cascade,

    username    text not null,
    allow_move  boolean not null,

    primary key (id),
    unique (username)
);
