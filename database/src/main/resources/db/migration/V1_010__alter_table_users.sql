alter table users
alter column registration_date type timestamp(6) using registration_date::timestamp(6);

alter table users
    alter column registration_date set default CURRENT_TIMESTAMP(6);

alter table users
alter column creation_date type timestamp(6) using creation_date::timestamp(6);

alter table users
    alter column creation_date set default CURRENT_TIMESTAMP(6);

alter table users
alter column modification_date type timestamp(6) using modification_date::timestamp(6);

alter table users
    alter column modification_date set default CURRENT_TIMESTAMP(6);


alter table users
    alter column registration_date drop not null;

alter table users
    alter column creation_date drop not null;

alter table users
    alter column modification_date drop not null;

alter table users
    alter column status set default 'ACTIVE';

create unique index users_email_uindex
    on users (email);

create unique index users_user_login_uindex
    on users (user_login);



