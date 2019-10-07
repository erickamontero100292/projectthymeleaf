drop table workday if exists;
drop table employee if exists;


drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 100 increment by 1;

create table workday (
	id bigint not null, 
	name  varchar(512), 
    number_hour integer,
	primary key (id)
);

create table employee (
	id bigint not null, 
	name  varchar(512), 
	workday_id bigint, 
	primary key (id)
);


alter table employee add constraint fk_employee_workday foreign key (workday_id) references workday;
