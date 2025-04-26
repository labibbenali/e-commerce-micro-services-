create table if not exists catagory
(
    id integer not null primary key,
    name varchar(255),
    description varchar(255)
);

create table if not exists product
(
    id integer not null primary key,
    name varchar(255),
    description varchar(255),
    available_quantity double precision not null ,
    price numeric (38,2),
    category_id integer
        constraint catagory_id_fk references catagory
);
create sequence if not exists catagory_seq increment by 1;
create sequence if not exists product_seq increment by 1;