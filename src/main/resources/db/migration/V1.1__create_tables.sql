create table employee_entity (
	id numeric not null,
	name varchar not null,
	address varchar not null,
	join_date timestamp not null,
	constraint employee_pk primary key (id)
);

create table user_entity (
    id numeric not null,
    username varchar(100) not null,
    password varchar not null,
    last_login timestamp,
    employee_id numeric not null,
    constraint employee_fk foreign key(employee_id) references employee_entity(id) on delete cascade,
    constraint user_pk primary key (id)
);

CREATE SEQUENCE "employee_entity_seq" START 100000 INCREMENT 10 MINVALUE 100000 MAXVALUE 10000000 OWNED BY "employee_entity"."id";
CREATE SEQUENCE "user_entity_seq" START 100000 INCREMENT 10 MINVALUE 100000 MAXVALUE 10000000 OWNED BY "user_entity"."id";
