alter table users
alter column status type varchar(25) using status::varchar(25);

alter table users
alter column city type varchar(50) using city::varchar(50);

