INSERT INTO users (id, first_name, user_login, user_password, email, status, city, registration_date, creation_date, modification_date, activation_code)
VALUES
    (1, 'Vladimir', 'strjke', '2222', 'zagvladimir88@gmail.com', 'ACTIVE', 'Zhlobin', '2023-02-02 17:48:01.463007', '2023-02-02 17:48:01.463007', '2023-02-02 17:48:01.463007', 'abcdef'),
    (2, 'Ivan', 'van', '2222', 'vanya@gmail.com', 'ACTIVE', 'Zhlobin', '2023-02-03 19:00:00.184000', '2023-02-03 19:00:00.184000', '2023-02-03 19:00:00.184000', 'abc');
select SETVAL('users_id_seq', 2);


INSERT INTO roles (id, name, creation_date, modification_date, status)
VALUES (1, 'ROLE_ADMIN', '2022-09-07 18:36:34.381538', '2022-09-07 18:36:34.381538', 'ACTIVE'),
 (2, 'ROLE_USER', '2022-09-07 18:36:34.381538', '2022-09-07 18:36:34.381538', 'ACTIVE'),
 (3, 'ROLE_MODERATOR', '2022-09-07 18:38:34.381538', '2022-09-07 18:38:34.381538', 'ACTIVE'),
 (4, 'ROLE_ANONYMOUS', '2022-09-19 15:15:21.167000', '2022-09-19 15:15:21.167000', 'ACTIVE');
select SETVAL('roles_id_seq', 4);

INSERT INTO user_roles (user_id, role_id, creation_date, modification_date)
VALUES (1, 1, '2023-02-03 17:32:57.000000', '2023-02-03 17:32:57.000000');

INSERT INTO tails (id, user_id, type, city, address, creation_date, modification_date, status, description)
VALUES (1, 1, 'Cat', 'Zhlobin', '17-5', '2023-02-04 09:10:21.000000', '2023-02-04 09:10:21.000000', 'ACTIVE','test cat'),
       (2, 2, 'Dog', 'Zhlobin', '18-9', '2023-02-04 09:10:21.000000', '2023-02-04 09:10:21.000000', 'ACTIVE','test dog');
select SETVAL('tails_id_seq', 4);

INSERT INTO images (id, tail_id, link, creation_date, modification_date, status)
VALUES (1, 1, 'testlink', '2023-02-04 09:41:40.000000', '2023-02-04 09:41:40.000000', 'ACTIVE'),
       (2, 2, 'testlink2', '2023-02-04 09:41:40.000000', '2023-02-04 09:41:40.000000', 'ACTIVE');
select SETVAL('images_id_seq', 2);
