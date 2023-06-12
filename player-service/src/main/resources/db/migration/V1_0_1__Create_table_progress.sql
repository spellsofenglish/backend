create table if not exists progress
(
    id uuid not null,
    progress   double precision,
    total_points   int4,
    game_level  int4
);

alter table progress add primary key (id);
alter table player add foreign key (progress_id) references progress (id) on DELETE cascade;