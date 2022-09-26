# WebStore

Docker script:

docker run --name store-postgres -p 5433:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=webstore -d postgres

Sql scripts:

create table if not exists products
(
id      bigserial            not null
constraint products_pk
primary key,
name character varying(250),
price bigint,
creation_date date
);

create table if not exists users
(
id      bigserial            not null
constraint users_pk
primary key,
name character varying(250),
email character varying(250),
password character varying(250),
role character varying(250),
sole character varying(250)
)

Paths:
- GUEST:
  - /products
- USER:
  -  /cart
- ADMIN:
  - /product
- ALL:
  - /login
  - /logout
  - /registration
