package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import java.util.*;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    //public List<Faculty> getFacultiesByColour(String colour) {
    //   if (colour == null && colour.isBlank()) {
    //      throw  new NullPointerException("Введите цвет!");
    // }
    //  return faculties.values().stream()
    //         .filter((f -> f.getColour().equals(colour)))
    //       .collect(Collectors.toList());
    // }
}
