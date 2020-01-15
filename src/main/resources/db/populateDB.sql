DELETE
FROM COURSES;
DELETE
FROM MENUS;
DELETE
FROM RESTAURANTS;
DELETE
FROM USER_ROLES;
DELETE
FROM USERS;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('Andrey', 'andrey@yandex.ru', '{noop}123456'),
       ('Vladimir', 'vladimr@yandex.ru', '{noop}qwerty'),
       ('Vasiliy', 'vasiliy@yandex.ru', '{noop}ytrewq'),
       ('Dmitriy', 'dmitriy@yandex.ru', '{noop}ytrewq1234'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_USER', 100001),
       ('ROLE_USER', 100002),
       ('ROLE_USER', 100003),
       ('ROLE_ADMIN', 100004),
       ('ROLE_USER', 100004);

INSERT INTO restaurants (name)
VALUES ('Domino`s pizza'),
       ('McDonald`s'),
       ('KFC');

INSERT INTO MENUS (restaurant_id, name)
VALUES (100005, 'Lunch'),
       (100006, 'Business lunch'),
       (100007, 'Afternoon Snack');

INSERT INTO COURSES (menu_id, name, price)
VALUES (100008, 'Pizza Pepperoni', 100),
       (100008, 'Pizza Margarita', 120),
       (100008, 'Salad', 40),
       (100009, 'Сheeseburger', 50),
       (100009, 'Big Mac', 60),
       (100010, 'Hot-Dog', 40),
       (100010, 'Longer Cheese', 50);
--
INSERT INTO votes (restaurant_id, user_id)
VALUES (100005, 100000),
       (100006, 100001),
       (100007, 100002),
       (100007, 100004);
