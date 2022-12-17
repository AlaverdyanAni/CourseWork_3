package ru.hogwarts.school.exception;

import ru.hogwarts.school.model.Student;

public class StudentNotFoundException extends RuntimeException {
    private final long id;

    public StudentNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}