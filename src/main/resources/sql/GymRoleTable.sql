create table gym_roles
(
    id   integer default nextval('my_shema.gym_roles_id_seq'::regclass) not null
        primary key,
    role text
);

alter table gym_roles
    owner to postgres;