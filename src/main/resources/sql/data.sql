
INSERT INTO users (username, password, email, phone_number,  description) VALUES ('tigo', '{noop}tigo', 'tigo@example.com', '1234567890','this is tigo');
INSERT INTO user_roles (username, role) VALUES ('tigo', 'ROLE_USER');
INSERT INTO user_roles (username, role) VALUES ('tigo', 'ROLE_ADMIN');

INSERT INTO users (username, password, email, phone_number,  description) VALUES ('john', '{noop}johnpw', 'john@example.com', '0987654321','this is john');
INSERT INTO user_roles (username, role) VALUES ('john', 'ROLE_ADMIN');

INSERT INTO users (username, password, email, phone_number,  description) VALUES ('mary', '{noop}marypw', 'mary@example.com', '55555555','this is mary');
INSERT INTO user_roles (username, role) VALUES ('mary', 'ROLE_USER');