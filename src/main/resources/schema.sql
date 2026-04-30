CREATE TABLE users (
    user_id serial PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) not null unique ,
    password VARCHAR(255) not null,
    token_version int NOT NULL DEFAULT 0,
    created_at timestamp
);
CREATE TABLE posts (
    post_id serial PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL ,
    image VARCHAR(255),
    user_id int not null,
    created_at timestamp,
    constraint fk_key foreign key (user_id) references users(user_id) on update cascade on delete cascade

);
drop table users,posts;
truncate table users restart identity cascade;