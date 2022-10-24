create table if not exists products
(
    id      bigserial            not null
    constraint products_pk
    primary key,
    name character varying(250),
	price bigint,
	creation_date timestamp
);