CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(150) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_users_last_name ON users(last_name);