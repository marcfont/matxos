create database races;

create table registration (id bigint AUTO_INCREMENT PRIMARY KEY, race VARCHAR(50), route VARCHAR(50), name VARCHAR(150), surname1 VARCHAR(150), surname2 VARCHAR(150), bibname VARCHAR(50), birthday date, dni VARCHAR(20), telf VARCHAR(50), town VARCHAR(150), club VARCHAR(150), feec VARCHAR(20), telfemer VARCHAR(250), gender VARCHAR(5), size VARCHAR(5), email VARCHAR(150), payment_id bigint, payment_date date, bib VARCHAR(10), is_here BOOL, is_completed BOOL, is_solidari BOOL,comment VARCHAR(200));

create table payment_order (id bigint AUTO_INCREMENT PRIMARY KEY, amount DOUBLE, status VARCHAR(50), response VARCHAR(150), auth_code VARCHAR(50));

create table time_reads (race VARCHAR(50), bib VARCHAR(50), control VARCHAR(50), time bigint, PRIMARY KEY (race, control, bib));


create table control (id VARCHAR(4), race VARCHAR(50), name VARCHAR(50), orderC int, PRIMARY KEY (id, race));
insert into control values ('SOR', 'MATXOS18', 'Sortida' ,0 );
insert into control values ('VAL', 'MATXOS18', 'Les Valls' ,1 );
insert into control values ('BELL', 'MATXOS18', 'Bellmunt' ,2 );
insert into control values ('SAL', 'MATXOS18', 'Salgueda' ,3 );
insert into control values ('BAR', 'MATXOS18', 'Sant Bartomeu' ,4 );
insert into control values ('PUI', 'MATXOS18', 'Puigsacalm' ,5 );
insert into control values ('PR1', 'MATXOS18', 'Prat de la vola 1' ,6 );
insert into control values ('CAB', 'MATXOS18', 'Cabrera' ,7 );
insert into control values ('PR2', 'MATXOS18', 'Prat de la vola 2' ,8 );
insert into control values ('COL', 'MATXOS18', 'Collsaplana' ,9 );
insert into control values ('SPT', 'MATXOS18', 'Sant Pere' ,10 );
insert into control values ('ARR', 'MATXOS18', 'Arribada' ,11 );

insert into control values ('SOR', 'TASTMAT18', 'Sortida' ,0 );
insert into control values ('VAL', 'TASTMAT18', 'Les Valls' ,1 );
insert into control values ('BELL', 'TASTMAT18', 'Bellmunt' ,2 );
insert into control values ('SPT', 'TASTMAT18', 'Sant Pere' ,3 );
insert into control values ('ARR', 'TASTMAT18', 'Arribada' ,4 );
