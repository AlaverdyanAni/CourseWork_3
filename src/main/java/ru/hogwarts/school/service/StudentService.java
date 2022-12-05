package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import java.util.List;
import ru.hogwarts.school.repositories.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student readStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
    public List<Student> findStudentsByAgeBetween(int minAge, int maxAge){
        return studentRepository.findStudentsByAgeBetween(minAge,maxAge);
    }
    public String findStudentFaculty(long id){
        Student student=readStudent(id);
        if (student==null){
            return null;
        }
        Faculty faculty=student.getFaculty();
        if (faculty==null) {
            return null;
        }
        return faculty.getName();
    }

   // public List<Student> getStudentsByAge(int age) {
    //    return studentRepository.;}

}
