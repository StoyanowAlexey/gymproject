create table gym_workers
(
    id       integer default nextval('my_shema.gym_list_id_seq'::regclass) not null
        constraint gym_person_pkey
            primary key,
    username varchar(255),
    role_id  integer default 102
        constraint fknem8d2ebsrp0s5ex7is1di55u
            references gym_roles
            on update cascade on delete set default,
    password text
);

alter table gym_workers
    owner to postgres;