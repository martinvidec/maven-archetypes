-- Database schema for cloud-ready web application

-- Create sequence for entity IDs
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 1 INCREMENT 1;

-- Users table
CREATE TABLE users (
    id BIGINT PRIMARY KEY DEFAULT nextval('hibernate_sequence'),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

-- User roles table
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_enabled ON users(enabled);
CREATE INDEX idx_users_created_at ON users(created_at);
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);

-- Insert default admin user (password should be set via application)
INSERT INTO users (username, email, first_name, last_name, enabled) 
VALUES ('admin', 'admin@example.com', 'Admin', 'User', true);

INSERT INTO user_roles (user_id, role) 
VALUES ((SELECT id FROM users WHERE username = 'admin'), 'ADMIN');