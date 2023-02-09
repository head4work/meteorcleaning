DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM orders;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');
INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 3);

 INSERT INTO orders (name, last_name, email, address, phone,housing_type,square_ft,bedrooms,bathrooms,
                     half_bathrooms,green_clean,deep_clean,microwave_clean,refrigerator_clean,
                    oven_clean,dishes_clean,windows,cabinets,date_time,estimated_price,estimated_time )
 VALUES ('aName', 'Last Name', 'aaaaaaaaaa@google.com', '1220 N Ogden dr', '12321321',1,'123','2','1','0',true,true,false,true,false,false,1,
         2,'2023-01-26 15:00:00.000000','220','1hours: 40 min' ),
        ('bName', 'Last Name', 'bemail@google.com', '1220 N Ogden dr', '12321321',2,'123','2','1','0',true,true,false,true,false,false,'8',
         '6','2023-01-26 11:00:00.000000','220','1hours: 40 min' ),
        ('cName', 'Last Name', 'cemail@google.com', '1220 N Ogden dr', '12321321',1,'123','2','1','0',false,true,false,true,false,false,'0',
         '0','2023-01-27 15:00:00.000000','220','1hours: 40 min' ),
        ('dName', 'Last Name', 'femail@google.com', '1220 N Ogden dr', '12321321',3,'123','2','1','0',false,true,false,true,false,false,'2',
         '0','2023-01-28 11:00:00.000000','220','1hours: 40 min' ),
        ('fName', 'Last Name', 'gemail@google.com', '1220 N Ogden dr', '12321321',2,'123','2','1','0',false,true,false,true,false,false,'0',
         '0','2023-12-29 11:00:00.000000','220','1hours: 40 min' ),
        ('gName', 'Last Name', 'jemail@google.com', '1220 N Ogden dr', '12321321',1,'123','2','1','0',false,true,false,true,false,false,'1',
         '0','2022-12-20 11:00:00.000000','220','1hours: 40 min' ),
        ('hName', 'Last Name', 'kemail@google.com', '1220 N Ogden dr', '12321321',2,'123','2','1','0',false,true,false,true,false,false,'0',
         '6','2022-12-21 15:00:00.000000','220','1hours: 40 min' )




