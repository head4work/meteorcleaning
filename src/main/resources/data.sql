DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM orders;


 INSERT INTO orders (name, last_name, email, address, phone,housing_type,square_ft,bedrooms,bathrooms,
                     half_bathrooms,green_clean,deep_clean,microwave_clean,refrigerator_clean,
                    oven_clean,dishes_clean,windows,cabinets,date_time,estimated_price,estimated_time )
 VALUES ('Name', 'Last Name', 'email@google.com', '1220 N Ogden dr', '12321321','House','123','2','1','0',false,true,false,true,false,false,'0',
         '0','2022-11-25 11:00:00.000000','220$','1hours: 40 min' ),
        ('Name', 'Last Name', 'email@google.com', '1220 N Ogden dr', '12321321','House','123','2','1','0',false,true,false,true,false,false,'0',
         '0','2022-11-26 11:00:00.000000','220$','1hours: 40 min' ),
        ('Name', 'Last Name', 'email@google.com', '1220 N Ogden dr', '12321321','House','123','2','1','0',false,true,false,true,false,false,'0',
         '0','2022-11-27 11:00:00.000000','220$','1hours: 40 min' ),
        ('Name', 'Last Name', 'email@google.com', '1220 N Ogden dr', '12321321','House','123','2','1','0',false,true,false,true,false,false,'0',
         '0','2022-11-28 11:00:00.000000','220$','1hours: 40 min' ),
        ('Name', 'Last Name', 'email@google.com', '1220 N Ogden dr', '12321321','House','123','2','1','0',false,true,false,true,false,false,'0',
         '0','2022-11-29 11:00:00.000000','220$','1hours: 40 min' ),
        ('Name', 'Last Name', 'email@google.com', '1220 N Ogden dr', '12321321','House','123','2','1','0',false,true,false,true,false,false,'0',
         '0','2022-11-30 11:00:00.000000','220$','1hours: 40 min' ),
        ('Name', 'Last Name', 'email@google.com', '1220 N Ogden dr', '12321321','House','123','2','1','0',false,true,false,true,false,false,'0',
         '0','2022-12-15 11:00:00.000000','220$','1hours: 40 min' )




