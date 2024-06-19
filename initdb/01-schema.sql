CREATE TABLE account (
                         id SERIAL PRIMARY KEY,
                         username VARCHAR(255) UNIQUE,
                         gender VARCHAR(20),
                         age INT,
                         creation_date TIMESTAMP
);