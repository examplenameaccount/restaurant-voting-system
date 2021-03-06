drop table if exists courses CASCADE;
drop table if exists menus CASCADE;
drop table if exists restaurants CASCADE;
drop table if exists user_roles CASCADE;
drop table if exists users CASCADE;
drop table if exists votes CASCADE;
drop sequence global_seq if exists;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

create table courses
(
    id      INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name    varchar(100) not null,
    price   integer      not null,
    menu_id integer      not null
);
create table menus
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    restaurant_id integer      not null,
    date_time     date default now()
);
create table restaurants
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name varchar(100) not null
);
create table user_roles
(
    user_id integer not null,
    role    varchar(255)
);
create table users
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name       varchar(255)            not null,
    email      varchar(255)            not null,
    password   varchar(255)            not null,
    registered timestamp default now() not null,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
create table votes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    local_date    date default now() not null,
    restaurant_id integer            not null,
    user_id       integer            not null
);

alter table users add constraint users_unique_email_idx unique (email);
alter table votes add constraint votes_unique_user_id_local_date_idx unique (user_id, local_date);
alter table courses add constraint courses_menu_id_fk foreign key (menu_id) references menus on delete cascade;
alter table courses add constraint courses_unique_menu_id_name_idx unique (menu_id, name);
alter table menus add constraint menu_restaurant_id_fk foreign key (restaurant_id) references restaurants on delete cascade;
alter table menus add constraint menus_unique_date_time_restaurant_id_idx unique (date_time, restaurant_id);
alter table user_roles add constraint user_roles_user_id_fk foreign key (user_id) references users ON DELETE CASCADE;
alter table user_roles add constraint user_roles_idx UNIQUE (user_id, role);
alter table votes add constraint votes_restaurant_id_fk foreign key (restaurant_id) references restaurants on delete cascade;
alter table votes add constraint votes_user_id_fk foreign key (user_id) references users on delete cascade;
alter table restaurants add constraint restaurants_unique_name_idx unique (name);