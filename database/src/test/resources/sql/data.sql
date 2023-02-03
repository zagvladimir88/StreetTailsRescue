INSERT INTO users (id, first_name, user_login, user_password, email, status, city, registration_date, creation_date, modification_date, activation_code)
VALUES
    (1, 'Vladimir', 'strjke', '2222', 'zagvladimir88@gmail.com', 'ACTIVE', 'Zhlobin', '2023-02-02 17:48:01.463007', '2023-02-02 17:48:01.463007', '2023-02-02 17:48:01.463007', 'abcdef'),
    (2, 'Ivan', 'van', '2222', 'vanya@gmail.com', 'ACTIVE', 'Zhlobin', '2023-02-03 19:00:00.184000', '2023-02-03 19:00:00.184000', '2023-02-03 19:00:00.184000', 'abc');
select SETVAL('users_id_seq', 2);
