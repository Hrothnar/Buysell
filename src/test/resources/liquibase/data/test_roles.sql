--liquibase formatted sql
--changeset Kirill:role_data_1
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM users_roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');