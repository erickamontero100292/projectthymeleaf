INSERT INTO workday (id,name, number_daily_hour,number_week_hour) VALUES (1,'Jornada laboral 8H',8,40);
INSERT INTO workday (id,name, number_daily_hour,number_week_hour) VALUES (2,'Jornada laboral 12H',12,60);
INSERT INTO userapp (id,user,password,rol) VALUES (1,'admin','admin','ADMIN' );
INSERT INTO userapp (id,user ,password,rol) VALUES (2,'pperez','123','USER' );
INSERT INTO userapp (id,user,password,rol) VALUES (3,'mperez','123','USER' );
INSERT INTO employee (id,name,workday_id,user_id) VALUES (1,'Pepe Perez', 1,2 );
INSERT INTO employee (id,name,workday_id,user_id) VALUES (2,'Maria Perez', 1,3 );




