/* START */

/* [jooq ignore start] */
DROP TABLE IF EXISTS "employee"
/* [jooq ignore stop] */

CREATE TABLE employee (
	id numeric NOT NULL,
	"name" varchar NOT NULL,
	address varchar NOT NULL,
	join_date timestamptz NOT NULL,
	CONSTRAINT employee_pk PRIMARY KEY (id)
);
