INSERT INTO users VALUES ('tigo', '{noop}tigo');
INSERT INTO user_roles(username, role) VALUES ('tigo', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('tigo', 'ROLE_ADMIN');

INSERT INTO users VALUES ('john', '{noop}johnpw');
INSERT INTO user_roles(username, role) VALUES ('john', 'ROLE_ADMIN');

INSERT INTO users VALUES ('mary', '{noop}marypw');
INSERT INTO user_roles(username, role) VALUES ('mary', 'ROLE_USER');