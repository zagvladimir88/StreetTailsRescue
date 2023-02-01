CREATE TABLE users (
                       id serial PRIMARY KEY,
                       first_name VARCHAR(255),
                       user_login VARCHAR(255),
                       user_password VARCHAR(255),
                       email VARCHAR(255),
                       status VARCHAR(255),
                       city VARCHAR(255),
                       registration_date TIMESTAMP NOT NULL DEFAULT NOW(),
                       creation_date TIMESTAMP NOT NULL DEFAULT NOW(),
                       modification_date TIMESTAMP NOT NULL DEFAULT NOW(),
                       activation_code VARCHAR(255)
);

CREATE TABLE tails (
                       id serial PRIMARY KEY,
                       user_id INTEGER REFERENCES users(id),
                       type VARCHAR(255),
                       city VARCHAR(255),
                       address VARCHAR(255),
                       creation_date TIMESTAMP NOT NULL DEFAULT NOW(),
                       modification_date TIMESTAMP NOT NULL DEFAULT NOW(),
                       status VARCHAR(255)
);

CREATE TABLE roles (
                       id serial PRIMARY KEY,
                       name VARCHAR(255),
                       creation_date TIMESTAMP NOT NULL DEFAULT NOW(),
                       modification_date TIMESTAMP NOT NULL DEFAULT NOW(),
                       status VARCHAR(255)
);

CREATE TABLE user_roles (
                            user_id INTEGER REFERENCES users(id),
                            role_id INTEGER REFERENCES roles(id),
                            creation_date TIMESTAMP NOT NULL DEFAULT NOW(),
                            modification_date TIMESTAMP NOT NULL DEFAULT NOW()
);

