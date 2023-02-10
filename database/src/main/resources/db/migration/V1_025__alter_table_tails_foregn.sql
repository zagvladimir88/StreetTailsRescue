alter table tails
drop constraint tails_user_id_fkey;

alter table tails
    add foreign key (user_id) references users
        on delete cascade;

alter table images
drop constraint images_tails_id_fk;

alter table images
    add constraint images_tails_id_fk
        foreign key (tail_id) references tails
            on delete cascade;

