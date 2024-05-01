--liquibase formatted sql

--changeset Maksym:1

INSERT INTO Authors (name, surname)
VALUES ('Andrew', 'Washington');

INSERT INTO Authors (name, surname)
VALUES ('Ollie', 'Owell');

INSERT INTO Authors (name, surname)
VALUES ('John', 'Tremblay');

INSERT INTO Authors (name, surname)
VALUES ('Volodymyr', 'Zelenskyy');


