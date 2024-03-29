DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM orders;


INSERT INTO users (name, email, password, phone, address, zipcode)
VALUES ('User', 'user@yandex.ru', '$2y$10$CeBJ8dKEznuxhPPkvog2JeY94dB1GW2nJGqwf7USiS4hYVbDdO/US', '3235551122',
        '1220 ogden dr', '90046');
INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', '$2y$10$QI39bbgQP38tkCHEw9VDneD5YUiQvioCde7YMdPzuIzlZKNJUeouy'),
       ('Guest', 'guest@gmail.com', '$2y$10$SecTJE2Z4WCPJmcsan7j3ej5UQji/0rsDzthS9RsZyBtpgDlRYAfa');
INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 3);

INSERT INTO tokens(user_id, token, created, expire)
VALUES (2, '6c287d9b-b495-489c-a591-c24f916d422b', now(), now() + INTERVAL '26hours');


INSERT INTO orders (name, paid, last_name, email, address, phone, housing_type, square_ft, bedrooms, bathrooms,
                    half_bathrooms, green_clean, deep_clean, microwave_clean, refrigerator_clean,
                    oven_clean, dishes_clean, windows, cabinets, date_time, estimated_price, estimated_time, user_id)
VALUES ('aName', true, 'Last Name', 'aaaaaaaaaa@google.com', '1220 N Ogden dr', '12321321', 1, '123', '2', '1', '0',
        true,
        true, false, true, false, false, 1,
        2, '2023-08-23 15:00:00.000000', '220', '1hours: 40 min', 1),
       ('bName', false, 'Last Name', 'bemail@google.com', '1220 N Ogden dr', '12321321', 2, '123', '2', '1', '0', true,
        true,
        false, true, false, false, '8',
        '6', '2023-08-23 11:00:00.000000', '220', '1hours: 40 min', 1),
       ('cName', false, 'Last Name', 'cemail@google.com', '1220 N Ogden dr', '12321321', 1, '123', '2', '1', '0', false,
        true,
        false, true, false, false, '0',
        '0', '2023-01-27 15:00:00.000000', '220', '1hours: 40 min', 1),
       ('dName', false, 'Last Name', 'femail@google.com', '1220 N Ogden dr', '12321321', 3, '123', '2', '1', '0', false,
        true, false, true, false, false, '2',
        '0', '2023-01-28 11:00:00.000000', '220', '1hours: 40 min', null),
       ('fName', false, 'Last Name', 'gemail@google.com', '1220 N Ogden dr', '12321321', 2, '123', '2', '1', '0', false,
        true, false, true, false, false, '0',
        '0', '2022-12-29 11:00:00.000000', '220', '1hours: 40 min', null),
       ('gName', false, 'Last Name', 'jemail@google.com', '1220 N Ogden dr', '12321321', 1, '123', '2', '1', '0', false,
        true, false, true, false, false, '1',
        '0', '2022-12-20 11:00:00.000000', '220', '1hours: 40 min', null),
       ('hName', false, 'Last Name', 'kemail@google.com', '1220 N Ogden dr', '12321321', 2, '123', '2', '1', '0', false,
        true, false, true, false, false, '0',
        '6', '2022-12-21 15:00:00.000000', '220', '1hours: 40 min', null),


       ('acName', false, 'Last Name', 'acemail@google.com', '1220 N Ogden dr', '12321321', 1, '123', '2', '1', '0',
        false, true, false, true, false, false, '0',
        '0', '2023-01-28 15:00:00.000000', '220', '1hours: 40 min', null),
       ('bcName', false, 'Last Name', 'bcemail@google.com', '1220 N Ogden dr', '12321321', 1, '123', '2', '1', '0',
        false, true, false, true, false, false, '0',
        '0', '2023-01-29 15:00:00.000000', '220', '1hours: 40 min', null),
       ('ccName', false, 'Last Name', 'ccemail@google.com', '1220 N Ogden dr', '12321321', 1, '123', '2', '1', '0',
        false, true, false, true, false, false, '0',
        '0', '2023-01-30 15:00:00.000000', '220', '1hours: 40 min', null),
       ('accName', false, 'Last Name', 'accemail@google.com', '1220 N Ogden dr', '12321321', 1, '123', '2', '1', '0',
        false, true, false, true, false, false, '0',
        '0', '2023-01-31 15:00:00.000000', '220', '1hours: 40 min', null),
       ('abName', false, 'Last Name', 'abemail@google.com', '1220 N Ogden dr', '12321321', 2, '123', '2', '1', '0',
        true, true, false, true, false, false, '8',
        '6', '2023-02-01 11:00:00.000000', '220', '1hours: 40 min', null),
       ('babName', false, 'Last Name', 'babemail@google.com', '1220 N Ogden dr', '12321321', 2, '123', '2', '1', '0',
        true, true, false, true, false, false, '8',
        '6', '2023-02-02 11:00:00.000000', '220', '1hours: 40 min', null)












