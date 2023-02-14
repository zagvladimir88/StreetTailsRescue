alter table users
    rename column city to city_id;

alter table users
alter column city_id type int using city_id::int;

alter table users
    add constraint users_cities_id_fk
        foreign key (city_id) references cities;

alter table tails
    rename column city to city_id;

alter table tails
alter column city_id type int using city_id::int;

alter table tails
    add constraint tails_cities_id_fk
        foreign key (city_id) references cities;

