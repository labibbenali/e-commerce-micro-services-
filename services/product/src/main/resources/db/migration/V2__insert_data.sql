-- Insert categories
insert into catagory (id, name, description)
values
    (nextval('catagory_seq'), 'Electronics', 'Electronic gadgets and devices'),
    (nextval('catagory_seq'), 'Books', 'Various genres of books'),
    (nextval('catagory_seq'), 'Clothing', 'Men and Women clothing');

-- Insert products
insert into product (id, name, description, available_quantity, price, category_id)
values
    (nextval('product_seq'), 'Smartphone', 'Latest model smartphone', 10, 799.99, 1),
    (nextval('product_seq'), 'Laptop', 'High-performance laptop', 5, 1299.99, 1),
    (nextval('product_seq'), 'Fantasy Novel', 'Epic fantasy book', 20, 19.99, 2),
    (nextval('product_seq'), 'T-shirt', '100% cotton T-shirt', 50, 9.99, 3);
