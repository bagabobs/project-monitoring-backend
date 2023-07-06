create table employees (
	id numeric not null,
	name varchar not null,
	address varchar not null,
	join_date timestamptz not null,
	constraint employee_pk primary key (id)
);