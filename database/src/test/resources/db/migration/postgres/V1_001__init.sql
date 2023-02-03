create table if not exists roles
(
    id                serial
    primary key,
    name              varchar(255),
    creation_date     timestamp default now() not null,
    modification_date timestamp default now() not null,
    status            varchar(255)
    );

alter table roles
    owner to test;

create table if not exists users
(
    id                serial
    primary key,
    first_name        varchar(255),
    user_login        varchar(255),
    user_password     varchar(255),
    email             varchar(255),
    status            varchar(25)  default 'ACTIVE'::character varying,
    city              varchar(50),
    registration_date timestamp(3) default CURRENT_TIMESTAMP(3),
    creation_date     timestamp(3) default CURRENT_TIMESTAMP(3),
    modification_date timestamp(3) default CURRENT_TIMESTAMP(3),
    activation_code   varchar(255)
    );

alter table users
    owner to test;

create unique index if not exists users_email_uindex
    on users (email);

create unique index if not exists users_user_login_uindex
    on users (user_login);

create table if not exists tails
(
    id                serial
    primary key,
    user_id           integer
    references users,
    type              varchar(255),
    city              varchar(255),
    address           varchar(255),
    creation_date     timestamp default now() not null,
    modification_date timestamp default now() not null,
    status            varchar(255)
    );

alter table tails
    owner to test;

create table if not exists user_roles
(
    user_id           integer
    references users,
    role_id           integer
    references roles,
    creation_date     timestamp default now() not null,
    modification_date timestamp default now() not null
    );

alter table user_roles
    owner to test;




