DELETE FROM tailor_orders WHERE customer_id = 1;
DELETE FROM customers WHERE id = 1;

INSERT INTO customers(id, email, first_name, last_name, middle_name, phone)
VALUES
    (1, 'test1@mail.com', 'andrew', 'sokolof', 'sergeevich', '88888');

INSERT INTO tailor_orders(id, name, country, defects, manufacturer, acceptance_date, order_state, customer_id, wear_degree, acceptor_id)
VALUES
    (1, 'Shirt', 'Malasya', 'hole', 'Sun', '2020-07-07', 0, 1, null, 1);

