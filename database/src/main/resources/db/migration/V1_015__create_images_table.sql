create table images
(
    id                bigserial
        primary key,
    tail_id           int
        constraint images_tails_id_fk
            references tails,
    link              varchar(255),
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6),
    status            varchar(25)  default 'ACTIVE'
);

