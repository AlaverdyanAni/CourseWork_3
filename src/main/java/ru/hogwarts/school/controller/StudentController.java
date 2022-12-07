package ru.hogwarts.school.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping //POST http://localhost:8080/student/2
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("{id}") //GET http://localhost:8080/student/2
    public ResponseEntity<Student> readStudent(@PathVariable Long id) {
        Student student = studentService.readStudent(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping //PUT http://localhost:8080/student/2
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        if (updatedStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}") //Delete http://localhost:8080/student/2
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping //GET http://localhost:8080/student/2
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping //GET http://localhost:8080/student/18,20
    public ResponseEntity<List<Student>> findStudentsByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        return ResponseEntity.ok(studentService.findStudentsByAgeBetween(minAge, maxAge));
    }

    @GetMapping("/{id}/faculty") //GET http://localhost:8080/student/1/faculty
    public  ResponseEntity <Faculty> findFacultyStudent(@PathVariable Long id){
        return ResponseEntity.ok(studentService.findStudentFaculty(id));
    }

    // @GetMapping("/age") //GET http://localhost:8080/students/2
    // public ResponseEntity<List<Student>> getStudentsByAge(@RequestParam Integer age){
    //    if (age<7||age>60){
    //        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //   }
    //  return ResponseEntity.ok(studentService.getStudentsByAge(age));
    // }
}

