--liquibase formatted sql

--changeset Maksym:1

INSERT INTO Authors (id, name, surname)
VALUES (1, 'Andrew', 'Washington');

INSERT INTO Authors (id, name, surname)
VALUES (2, 'Ollie', 'Owell');

INSERT INTO Authors (id, name, surname)
VALUES (3, 'John', 'Tremblay');

INSERT INTO Authors (id, name, surname)
VALUES (4, 'Volodymyr', 'Zelenskyy');


