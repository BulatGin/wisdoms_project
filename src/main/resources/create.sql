CREATE DATABASE wisdomsdb;

create table wisdomsdb.public.wisdoms
(
  id serial not null
    constraint wisdoms_pk
    primary key,
  wisdom varchar(255)
)
;