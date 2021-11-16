INSERT INTO gift_certificates (description, price, duration, last_update_date, name, create_date)
VALUES ('qwerty', 99, 3, '2017-01-23T12:34:56.123456789Z', 'qwerty', '2017-01-23T12:34:56.123456789Z');
INSERT INTO gift_certificates (description, price, duration, last_update_date, name, create_date)
VALUES ('qwerty', 99, 3, '2017-01-23T12:34:56.123456789Z', 'qwerty', '2017-01-23T12:34:56.123456789Z');
INSERT INTO gift_certificates (description, price, duration, last_update_date, name, create_date)
VALUES ('qwerty', 99, 3, '2017-01-23T12:34:56.123456789Z', 'qwerty', '2017-01-23T12:34:56.123456789Z');

INSERT INTO tags (name) VALUES ('Bob');
INSERT INTO tags (name) VALUES ('John');
INSERT INTO tags (name) VALUES ('Michael');
INSERT INTO tags (name) VALUES ('Tom');

INSERT INTO certificate_tag_maps(gift_certificate_id, tag_id) VALUES (1, 1);
INSERT INTO certificate_tag_maps(gift_certificate_id, tag_id) VALUES (1, 3);
INSERT INTO certificate_tag_maps(gift_certificate_id, tag_id) VALUES (1, 4);

INSERT INTO users(forename, surname) VALUES ('Jack', 'Smith');
INSERT INTO users(forename, surname) VALUES ('John', 'Wick');
INSERT INTO users(forename, surname) VALUES ('Bob', 'Brown');
INSERT INTO users(forename, surname) VALUES ('Rick', 'Black');

INSERT INTO orders(purchase_timestamp, total_cost, user_id, certificate_id)
VALUES ('2017-01-23T12:34:56.123456789Z', 99, 1, 1);