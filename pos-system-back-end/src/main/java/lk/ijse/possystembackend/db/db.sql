CREATE DATABASE IF NOT EXISTS possystem;

USE possystem;

-- customer table
CREATE TABLE customer (
                          customer_id     VARCHAR(20) PRIMARY KEY,
                          name            VARCHAR(50) NOT NULL,
                          address         VARCHAR(50) NOT NULL,
                          contact         VARCHAR(20) NOT NULL
);

-- item table
CREATE TABLE item (
                      item_id         VARCHAR(20) PRIMARY KEY,
                      description     VARCHAR(50) NOT NULL,
                      unit_price      DECIMAL(10, 2) NOT NULL,
                      qty_on_hand     INT NOT NULL
);

-- orders table
CREATE TABLE orders (
                        order_id        VARCHAR(20) PRIMARY KEY,
                        order_date      DATETIME NOT NULL,
                        total           DECIMAL(10, 2) NOT NULL,
                        discount_value  DECIMAL(10, 2) NOT NULL,
                        new_total       DECIMAL(10, 2) NOT NULL,
                        customer_id     VARCHAR(20) NOT NULL,
                        CONSTRAINT FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
                        ON UPDATE CASCADE ON DELETE CASCADE

);

-- order_details table
CREATE TABLE order_details (
                               order_id        VARCHAR(20) NOT NULL,
                               item_id         VARCHAR(20) NOT NULL,
                               qty             INT NOT NULL,
                               unit_price      DECIMAL(10, 2) NOT NULL,
                               total           DECIMAL(10, 2) NOT NULL,
                               PRIMARY KEY (order_id, item_id),
                               CONSTRAINT FOREIGN KEY (order_id) REFERENCES orders(order_id)
                               ON UPDATE CASCADE ON DELETE CASCADE,
                               CONSTRAINT FOREIGN KEY (item_id) REFERENCES item(item_id)
                               ON UPDATE CASCADE ON DELETE CASCADE
);