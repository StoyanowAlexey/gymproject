create table gym_visitors
(
    id               integer default nextval('my_shema.gym_visotors_id_seq'::regclass) not null
        constraint gym_visotors_pkey
            primary key,
    name             text,
    age              integer,
    gender           text,
    telegram_account text,
    season_ticket_id integer default 2
        constraint fkq1oxaho3bbp04650q8vu07n2d
            references gym_season_tickets,
    phone_number     text,
    gmail            text,
    photo            text
);

alter table gym_visitors
    owner to postgres;