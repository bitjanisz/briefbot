-- Add password_hash column to users table for local authentication
ALTER TABLE users ADD COLUMN password_hash VARCHAR(255);

-- Insert sample local user for testing (email/password login)
-- Email: admin@briefbot.com
-- Password: Admin123 (BCrypt hashed)
INSERT INTO users (email, given_name, family_name, password_hash, created_at)
VALUES (
    'admin@briefbot.com',
    'Admin',
    'User',
    '$2a$10$xqKXKGnD5H9ViJH8CqVh8Oe2QtblP00z1p/r2S7uNbHnN61nIVSj6', -- BCrypt hash of "Admin123"
    CURRENT_TIMESTAMP
);

-- Insert sample OAuth2 user (Google login)
-- Email: borapltest@gmail.com
-- Login: borapltest
-- This user logs in via Google OAuth2
INSERT INTO users (email, given_name, family_name, oidc_sub, picture_url, created_at)
VALUES (
    'borapltest@gmail.com',
    'google',
    'user',
    '107081570934355323777',
    NULL,
    CURRENT_TIMESTAMP
);

-- Create default account
INSERT INTO accounts (name, created_at)
VALUES (
    'Default Account',
    CURRENT_TIMESTAMP
);

-- Assign admin user to default account as OWNER
INSERT INTO user_accounts (user_id, account_id, role, created_at)
SELECT u.id, a.id, 'OWNER', CURRENT_TIMESTAMP
FROM users u, accounts a
WHERE u.email = 'admin@briefbot.com'
  AND a.name = 'Default Account';

-- Assign Google OAuth2 user to default account as MEMBER
INSERT INTO user_accounts (user_id, account_id, role, created_at)
SELECT u.id, a.id, 'MEMBER', CURRENT_TIMESTAMP
FROM users u, accounts a
WHERE u.email = 'borapltest@gmail.com'
  AND a.name = 'Default Account';