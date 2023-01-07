--liquibase formatted sql

--changeset ania:1
CREATE  INDEX IF NOT EXISTS students_name_index ON students(name);

--changeset ania:2
CREATE INDEX IF NOT EXISTS faculties_name_colour_index ON faculties(name,colour);