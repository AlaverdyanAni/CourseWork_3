package ru.hogwarts.school.service;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import java.util.*;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository=studentRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(()->new FacultyNotFoundException(id));
    }

    public Faculty updateFaculty(long id,Faculty newFaculty) {
        Faculty faculty=readFaculty(id);
        faculty.setName(newFaculty.getName());
        faculty.setColour(newFaculty.getColour());
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(long id) {
        Faculty faculty=readFaculty(id);
        facultyRepository.delete(faculty);
        return faculty;
    }

    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    public List<Faculty> findFacultiesByNameOrColour(String nameOrColour){
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColourIgnoreCase(nameOrColour,nameOrColour);
    }
    public List<Student> findFacultyStudents(long facultyId){
        Faculty faculty=readFaculty(facultyId);
        return studentRepository.findStudentsByFacultyId(faculty.getId());
    }

    // public List<Faculty> getFacultiesByColour(String colour) {
    //   if (colour == null && colour.isBlank()) {
    //      throw  new NullPointerException("Введите цвет!");
    // }
    //  return faculties.values().stream()
    //         .filter((f -> f.getColour().equals(colour)))
    //       .collect(Collectors.toList());
    // }
}
