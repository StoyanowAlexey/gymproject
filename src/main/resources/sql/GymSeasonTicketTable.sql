create table gym_season_tickets
(
    id          integer default nextval('my_shema.gym_season_tickets_id_seq'::regclass) not null
        primary key,
    ticket_type text
);

alter table gym_season_tickets
    owner to postgres;