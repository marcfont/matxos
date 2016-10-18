create database races;

create table registration (id bigint AUTO_INCREMENT PRIMARY KEY, race VARCHAR(50), route VARCHAR(50), name VARCHAR(150), surname1 VARCHAR(150), surname2 VARCHAR(150), bibname VARCHAR(50), birthday date, dni VARCHAR(20), telf VARCHAR(50), town VARCHAR(150), club VARCHAR(150), feec VARCHAR(20), telfemer VARCHAR(250), gender VARCHAR(5), size VARCHAR(5), email VARCHAR(150), payment_id bigint, payment_date date, bib VARCHAR(10), is_here BOOL);

create table payment_order (id bigint AUTO_INCREMENT PRIMARY KEY, amount DOUBLE, status VARCHAR(50), response VARCHAR(150), auth_code VARCHAR(50));

create table time_reads (race VARCHAR(50), bib VARCHAR(50), control VARCHAR(50), time bigint, PRIMARY KEY (race, control, bib));


