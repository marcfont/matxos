create database races;

create table registration (id bigint AUTO_INCREMENT PRIMARY KEY, race VARCHAR(50), route VARCHAR(50), name VARCHAR(150), surname1 VARCHAR(150), surname2 VARCHAR(150), bibname VARCHAR(50), birthday date, dni VARCHAR(20), telf VARCHAR(50), town VARCHAR(150), club VARCHAR(150), feec VARCHAR(20), telfemer VARCHAR(250), gender VARCHAR(5), size VARCHAR(5), email VARCHAR(150), payment_id bigint, payment_date date, bib VARCHAR(10), is_here BOOL, is_completed BOOL);

create table payment_order (id bigint AUTO_INCREMENT PRIMARY KEY, amount DOUBLE, status VARCHAR(50), response VARCHAR(150), auth_code VARCHAR(50));

create table time_reads (race VARCHAR(50), bib VARCHAR(50), control VARCHAR(50), time bigint, PRIMARY KEY (race, control, bib));


ALTER TABLE registration ADD is_waiting BOOL;
ALTER TABLE registration ADD is_approved BOOL;
ALTER TABLE registration ADD comment VARCHAR(200);

create table control (id VARCHAR(4), race VARCHAR(50), name VARCHAR(50), orderC int, PRIMARY KEY (id, race));
insert into control values ('SOR', 'MATXOS17', 'Sortida' ,0 );
insert into control values ('VAL', 'MATXOS17', 'Les Valls' ,1 );
insert into control values ('BELL', 'MATXOS17', 'Bellmunt' ,2 );
insert into control values ('SAL', 'MATXOS17', 'Salgueda' ,3 );
insert into control values ('BAR', 'MATXOS17', 'Sant Bartomeu' ,4 );
insert into control values ('PUI', 'MATXOS17', 'Puigsacalm' ,5 );
insert into control values ('PR1', 'MATXOS17', 'Prat de la vola 1' ,6 );
insert into control values ('CAB', 'MATXOS17', 'Cabrera' ,7 );
insert into control values ('PR2', 'MATXOS17', 'Prat de la vola 2' ,8 );
insert into control values ('COL', 'MATXOS17', 'Collsaplana' ,9 );
insert into control values ('SPT', 'MATXOS17', 'Sant Pere' ,10 );
insert into control values ('ARR', 'MATXOS17', 'Arribada' ,11 );

insert into control values ('SOR', 'TASTMAT17', 'Sortida' ,0 );
insert into control values ('VAL', 'TASTMAT17', 'Les Valls' ,1 );
insert into control values ('BELL', 'TASTMAT17', 'Bellmunt' ,2 );
insert into control values ('SPT', 'TASTMAT17', 'Sant Pere' ,3 );
insert into control values ('ARR', 'TASTMAT17', 'Arribada' ,4 );
