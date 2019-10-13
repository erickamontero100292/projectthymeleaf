drop table userapp if exists;
drop table workday if exists;
drop table employee if exists;
drop table registry if exists;


drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 100 increment by 1;

create table workday
(
    id                bigint not null,
    name              varchar(512),
    number_daily_hour integer,
    number_week_hour  integer,
    primary key (id)
);

create table employee
(
    id         bigint not null,
    name       varchar(512),
    workday_id bigint,
    user_id    bigint,
    primary key (id)
);

create table registry
(
    id            bigint not null,
    employee_id   bigint,
    date_registry timestamp,
    hours         bigint,
    primary key (id)
);

create table userapp
(
    id          bigint not null,
    date_create timestamp,
    email       varchar(512),
    password    varchar(512),
    primary key (id)
);


alter table employee
    add constraint fk_employee_workday foreign key (workday_id) references workday;
alter table employee
    add constraint fk_employee_user foreign key (user_id) references userapp;
alter table registry
    add constraint fk_employee_id foreign key (employee_id) references employee;
