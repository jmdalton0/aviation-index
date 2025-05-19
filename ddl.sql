DROP DATABASE IF EXISTS aviation_index;

CREATE DATABASE aviation_index;

USE aviation_index;

CREATE TABLE topic (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT,
    topic VARCHAR(255) NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES topic(id)
);

CREATE TABLE question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    topic_id BIGINT,
    position INT NOT NULL,
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(255) NOT NULL,
    FOREIGN KEY (topic_id) REFERENCES topic(id)
);

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM("ROLE_USER", "ROLE_ADMIN") NOT NULL,
    study_status VARCHAR(255),
    study_topic_id BIGINT, 
    study_question_id BIGINT, 
    FOREIGN KEY (study_topic_id) REFERENCES topic(id),
    FOREIGN KEY (study_question_id) REFERENCES question(id)
);

CREATE TABLE user_question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    active TINYINT NOT NULL,
    study_status VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE,
    UNIQUE (user_id, question_id)
);