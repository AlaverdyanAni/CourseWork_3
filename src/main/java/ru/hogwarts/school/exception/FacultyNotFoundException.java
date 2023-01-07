package ru.hogwarts.school.exception;

public class FacultyNotFoundException extends RuntimeException {
    private long id;

    public FacultyNotFoundException(long id) {
        this.id = id;
    }

    public FacultyNotFoundException(){

    }

    public long getId() {
        return id;
    }
}
