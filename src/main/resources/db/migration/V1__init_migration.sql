create table my_db.social_media_user
(
    id           bigint auto_increment
        primary key,
    date         varchar(255) not null,
    email        varchar(255) not null unique ,
    first_name   varchar(255) not null,
    gender       varchar(255) not null default 'NO_ONE',
    second_name  varchar(255) not null,
    password     varchar(255) not null,
    phone_number varchar(255) not null,
    username     varchar(255) not null unique
);