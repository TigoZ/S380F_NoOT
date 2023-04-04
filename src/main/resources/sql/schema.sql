CREATE TABLE IF NOT EXISTS blog
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    name        VARCHAR(255),
    subject     VARCHAR(255),
    body        VARCHAR(255),
    create_time TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    update_time TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (id)
);


create table if not exists attachment
(
    create_time  timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    update_time  timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    id           uuid                  default random_uuid() not null,
    content      blob,
    content_type varchar(255),
    filename     varchar(255),
    blog_id      bigint,
    primary key (id),
    foreign key (blog_id) references blog
);

DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    create_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    update_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    username    VARCHAR(50)  NOT NULL,
    password    VARCHAR(50)  NOT NULL,
    PRIMARY KEY (username)
);
CREATE TABLE IF NOT EXISTS user_roles
(
    create_time  timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    update_time  timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    user_role_id INTEGER GENERATED ALWAYS AS IDENTITY,
    username     VARCHAR(50)  NOT NULL,
    role         VARCHAR(50)  NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users (username)
);