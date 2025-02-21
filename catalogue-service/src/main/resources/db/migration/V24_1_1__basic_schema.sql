CREATE SCHEMA IF NOT EXISTS catalogue;

CREATE TABLE IF NOT EXISTS catalogue.t_product
(
    id serial PRIMARY KEY,
    c_title varchar(50) NOT NULL CHECK (LENGTH(TRIM(c_title)) >= 3),
    c_details varchar(1000)
);