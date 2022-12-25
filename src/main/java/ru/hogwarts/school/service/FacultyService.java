package ru.hogwarts.school.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import java.util.*;

@Service
@Profile("prodaction")
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository=studentRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Create faculty");
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty(long id) {
        logger.debug("Read faculty ID: {}", id);
        return facultyRepository.findById(id).orElseThrow(()->new FacultyNotFoundException(id));
    }

    public Faculty updateFaculty(long id,Faculty newFaculty) {
        logger.debug("Update faculty ID: {}", id);
        Faculty faculty=readFaculty(id);
        faculty.setName(newFaculty.getName());
        faculty.setColour(newFaculty.getColour());
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(long id) {
        logger.debug("Delete faculty ID: {}", id);
        Faculty faculty=readFaculty(id);
        facultyRepository.delete(faculty);
        return faculty;
    }

    public List<Faculty> getFaculties() {
        logger.debug("Get  all faculties");
        return facultyRepository.findAll();
    }

    public List<Faculty> findFacultiesByNameOrColour(String nameOrColour){
        logger.debug("Find  all faculties by name: {} or colour: {}", nameOrColour);
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColourIgnoreCase(nameOrColour,nameOrColour);
    }
    public List<Student> findFacultyStudents(long facultyId){
        logger.debug("Find students by faculty ID: {}", facultyId);
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
