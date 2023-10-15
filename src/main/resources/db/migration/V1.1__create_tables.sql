create table employee_entity (
	id numeric not null,
	name varchar not null,
	address varchar not null,
	join_date timestamp not null,
	constraint employee_pk primary key (id)
);

CREATE SEQUENCE "employee_entity_seq" START 100000 INCREMENT 10 MINVALUE 100000 MAXVALUE 10000000 OWNED BY "employee_entity"."id";
