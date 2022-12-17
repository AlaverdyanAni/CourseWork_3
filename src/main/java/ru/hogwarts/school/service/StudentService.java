package ru.hogwarts.school.service;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import java.util.List;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.repositories.StudentsList;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student readStudent(long id) {
        return studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException(id));
    }

    public Student updateStudent(long id,Student newStudent) {
        Student student=readStudent(id);
        student.setName(newStudent.getName());
        student.setAge(newStudent.getAge());
        return studentRepository.save(student);
    }

    public Student deleteStudent(long id) {
        Student student=readStudent(id);
        studentRepository.delete(student);
        return student;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
    public List<Student> findStudentsByAgeBetween(int minAge, int maxAge){
        return studentRepository.findStudentsByAgeBetween(minAge,maxAge);
    }
    public Faculty findStudentFaculty(long id){
        Student student=readStudent(id);
        if (student==null) {
            return null;
        }
        return student.getFaculty();
    }
    public int getStudentsCount(){
        return  studentRepository.getStudentsCount();
    }
    public double getStudentsAgeAvg(){
        return studentRepository.getStudentsAgeAvg();
    }
    public List<StudentsList> getLastStudentsById(int limit){
        return studentRepository.getLastStudentsById(limit);
    }

   // public List<Student> getStudentsByAge(int age) {
    //    return studentRepository.;}

}
