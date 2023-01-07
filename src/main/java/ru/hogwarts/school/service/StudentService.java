package ru.hogwarts.school.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import java.util.List;
import java.util.stream.Collectors;

import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.repositories.StudentsList;

@Service
@Profile("prodaction")
public class StudentService {
    private final StudentRepository studentRepository;
    Logger logger= LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.debug("Create student");
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student readStudent(long id) {
        logger.debug("Read student ID: {}", id);
        return studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException(id));
    }

    public Student updateStudent(long id,Student newStudent) {
        logger.debug("Update student ID: {}", id);
        Student student=readStudent(id);
        student.setName(newStudent.getName());
        student.setAge(newStudent.getAge());
        return studentRepository.save(student);
    }

    public Student deleteStudent(long id) {
        logger.debug("Delete student ID: {}", id);
        Student student=readStudent(id);
        studentRepository.delete(student);
        return student;
    }

    public List<Student> getStudents() {
        logger.debug("Get all students");
        return studentRepository.findAll();
    }

    public List<Student> findStudentsByAgeBetween(int minAge, int maxAge){
        logger.debug("Find students by age between min: {}, max: {}",minAge, maxAge);
        return studentRepository.findStudentsByAgeBetween(minAge,maxAge);
    }
    public Faculty findStudentFaculty(long id){
        logger.debug("Find student faculty, ID: {}", id);
        Student student=readStudent(id);
        if (student==null) {
            return null;
        }
        return student.getFaculty();
    }
    public int getStudentsCount(){
        logger.debug("Get students count");
        return  studentRepository.getStudentsCount();
    }
    public double getStudentsAgeAvg(){
        logger.debug("Get students average age");
        return studentRepository.getStudentsAgeAvg();
    }
    public List<StudentsList> getLastStudentsById(int limit){
        logger.debug("Get last students, limit: {}", limit);
        return studentRepository.getLastStudentsById(limit);
    }

    public List<String> getStudentsByNameStartsWith(String letter){
        logger.debug("Get Students by name starts with: {}", letter);
        return studentRepository.findAll()
                .stream()
                .map(s->s.getName())
                .filter(n->n.startsWith(letter))
                .sorted((s1,s2)->s1.compareTo(s2))
                .map(s->s.toUpperCase())
                .collect(Collectors.toList());
    }

    public Double getStudentsAgeAvgWithStream(){
        logger.debug("Get students average age with stream");
        return studentRepository.findAll()
                .stream()
                .mapToDouble(s->s.getAge())
                .average()
                .orElse(Double.NaN);
    }
}
