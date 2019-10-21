INSERT INTO workday (id,name, number_daily_hour,number_week_hour) VALUES (1,'Jornada laboral 8H',8,40);
INSERT INTO workday (id,name, number_daily_hour,number_week_hour) VALUES (2,'Jornada laboral 12H',12,60);
INSERT INTO userapp (id,user,password,rol) VALUES (1,'admin','admin','ADMIN' );
INSERT INTO userapp (id,user ,password,rol) VALUES (2,'pperez','123','USER' );
INSERT INTO userapp (id,user,password,rol) VALUES (3,'mperez','123','USER' );
INSERT INTO employee (id,name,workday_id,user_id) VALUES (1,'Pepe Perez', 1,2 );
INSERT INTO employee (id,name,workday_id,user_id) VALUES (2,'Maria Perez', 1,3 );
insert into registry (date_registry, employee_id, hours, id) values (PARSEDATETIME('26 Jul 2019, 00:00:00 AM','dd MMM yyyy, hh:mm:ss a','en') , 1, 2, 1);
insert into registry (date_registry, employee_id, hours, id) values (PARSEDATETIME('26 Jul 2019, 00:00:00 AM','dd MMM yyyy, hh:mm:ss a','en') , 2, 2, 4);
insert into registry (date_registry, employee_id, hours, id) values (PARSEDATETIME('27 Jul 2019, 00:00:00 AM','dd MMM yyyy, hh:mm:ss a','en') , 1, 2, 2);
insert into registry (date_registry, employee_id, hours, id) values (PARSEDATETIME('27 Jul 2019, 00:00:00 AM','dd MMM yyyy, hh:mm:ss a','en') , 2, 2, 5);
insert into registry (date_registry, employee_id, hours, id) values (PARSEDATETIME('28 Jul 2019, 00:00:00 AM','dd MMM yyyy, hh:mm:ss a','en') , 1, 2, 3);
insert into registry (date_registry, employee_id, hours, id) values (PARSEDATETIME('28 Jul 2019, 00:00:00 AM','dd MMM yyyy, hh:mm:ss a','en') , 2, 2, 6);



