create table if not exists users (
id bigint generated by default as identity not null primary key,
last_name varchar(100) not null,
first_name varchar(100) not null,
patronymic varchar(100) not null,
date_of_birth date,
email varchar(100) not null unique,
phone_number varchar(15) not null unique
);