ALTER TABLE students ADD  CONSTRAINT age_constraint CHECK (age>=16);
ALTER TABLE students ALTER COLUMN name SET NOT NULL;
ALTER TABLE students ADD CONSTRAINT name_unique UNIQUE (name);
ALTER  TABLE  faculties ADD CONSTRAINT name_colour_unique UNIQUE  (name, colour);
ALTER TABLE  students ALTER COLUMN age SET DEFAULT 20;
SELECT  students.name, students.age,faculties.name FROM students
    INNER JOIN faculties ON students.faculty_id = faculties.id;
SELECT students.name, students.age From students
    LEFT Join avatar on students.id = avatar.student_id;