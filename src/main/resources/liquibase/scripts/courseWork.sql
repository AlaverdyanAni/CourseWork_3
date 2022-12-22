--liquibase formatted sql

--changeset ania:1
CREATE  INDEX students_name_index ON students(name);

--changeset ania:2
CREATE INDEX faculties_name_colour_index ON faculties(name,colour);