USE spring_boot_practice;
SELECT database() AS current_db;

CREATE TABLE customers(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE orders(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    quantity SMALLINT NOT NULL,
    ordered_at TIMESTAMP,
    price DOUBLE CHECK(price > 0),
    customer_id INT,
    CONSTRAINT order_customer_fk FOREIGN KEY(customer_id) REFERENCES customers(id) ON DELETE SET NULL
);

CREATE TABLE clients(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    password CHAR(60) NOT NULL
);

SELECT * FROM customers;
SELECT * FROM orders;
SELECT * FROM clients;

-- DROP TABLE orders;
-- DROP TABLE customers;
-- DROP TABLE clients;
