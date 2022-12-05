package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentrepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentrepository=studentRepository;
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

    public List<Faculty> getFacultiesByColour(String colour){
        return facultyRepository.findFacultiesByColour(colour);
    }

    public Faculty findFacultiesByNameOrColour(String text){
        Faculty faculty=facultyRepository.findFacultyByNameIgnoreCase(text);
        if(faculty==null){
            faculty=facultyRepository.findFacultyByColourIgnoreCase(text);
        }
        return faculty;
    }
    public List<Student> findFacultyStudents(long id){
        Faculty faculty=readFaculty(id);
        if (faculty==null){
            return null;
        }
        return studentrepository.findStudentsByFacultyId(faculty.getId());
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
