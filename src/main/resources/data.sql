DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM orders;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '$2y$10$CeBJ8dKEznuxhPPkvog2JeY94dB1GW2nJGqwf7USiS4hYVbDdO/US'),
       ('Admin', 'admin@gmail.com', '$2y$10$QI39bbgQP38tkCHEw9VDneD5YUiQvioCde7YMdPzuIzlZKNJUeouy'),
       ('Guest', 'guest@gmail.com', '$2y$10$SecTJE2Z4WCPJmcsan7j3ej5UQji/0rsDzthS9RsZyBtpgDlRYAfa');
INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 3);

 INSERT INTO orders (name, last_name, email, address, phone,housing_type,square_ft,bedrooms,bathrooms,
                     half_bathrooms,green_clean,deep_clean,microwave_clean,refrigerator_clean,
                    oven_clean,dishes_clean,windows,cabinets,date_time,estimated_price,estimated_time, user_id )
 VALUES ('aName', 'Last Name', 'aaaaaaaaaa@google.com', '1220 N Ogden dr', '12321321',1,'123','2','1','0',true,true,false,true,false,false,1,
         2,'2023-01-26 15:00:00.000000','220','1hours: 40 min' ,1 ),
        ('bName', 'Last Name', 'bemail@google.com', '1220 N Ogden dr', '12321321',2,'123','2','1','0',true,true,false,true,false,false,'8',
         '6','2023-01-26 11:00:00.000000','220','1hours: 40 min',null ),
        ('cName', 'Last Name', 'cemail@google.com', '1220 N Ogden dr', '12321321',1,'123','2','1','0',false,true,false,true,false,false,'0',
         '0','2023-01-27 15:00:00.000000','220','1hours: 40 min',null ),
        ('dName', 'Last Name', 'femail@google.com', '1220 N Ogden dr', '12321321',3,'123','2','1','0',false,true,false,true,false,false,'2',
         '0','2023-01-28 11:00:00.000000','220','1hours: 40 min',null ),
        ('fName', 'Last Name', 'gemail@google.com', '1220 N Ogden dr', '12321321',2,'123','2','1','0',false,true,false,true,false,false,'0',
         '0','2023-12-29 11:00:00.000000','220','1hours: 40 min',null ),
        ('gName', 'Last Name', 'jemail@google.com', '1220 N Ogden dr', '12321321',1,'123','2','1','0',false,true,false,true,false,false,'1',
         '0','2022-12-20 11:00:00.000000','220','1hours: 40 min',null ),
        ('hName', 'Last Name', 'kemail@google.com', '1220 N Ogden dr', '12321321',2,'123','2','1','0',false,true,false,true,false,false,'0',
         '6','2022-12-21 15:00:00.000000','220','1hours: 40 min' ,null)




