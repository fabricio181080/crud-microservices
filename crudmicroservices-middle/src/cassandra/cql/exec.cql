CREATE KEYSPACE crudmicroservices WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3};

CREATE TABLE crudmicroservices.person (
	id bigint,
	name text,
	phone_number text,
	PRIMARY KEY (id));