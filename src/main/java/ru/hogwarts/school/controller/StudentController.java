package ru.hogwarts.school.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentsList;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping //POST http://localhost:8080/student
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("{id}") //GET http://localhost:8080/student/2
    public ResponseEntity<Student> readStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.readStudent(id));
    }

    @PutMapping("/{id}")//PUT http://localhost:8080/student
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("{id}") //Delete http://localhost:8080/student/2
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all") //GET http://localhost:8080/student/2
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getStudentsCount() {
        return ResponseEntity.ok(studentService.getStudentsCount());
    }

    @GetMapping("/{id}/faculty") //GET http://localhost:8080/student/1/faculty
    public ResponseEntity<Faculty> findStudentFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findStudentFaculty(id));
    }

    @GetMapping(params = {"minAge", "maxAge"})//GET http://localhost:8080/student?minAge=18&maxAge=20
    public ResponseEntity<List<Student>> findStudentsByAgeBetween(@RequestParam int minAge,
                                                                  @RequestParam int maxAge) {
        return ResponseEntity.ok(studentService.findStudentsByAgeBetween(minAge, maxAge));
    }

    @GetMapping("/age-avg")
    public ResponseEntity<Double> getStudentsAgeAvg() {
        return ResponseEntity.ok(studentService.getStudentsAgeAvg());
    }

    @GetMapping("/age-avg-with-stream")
    public ResponseEntity<Double> getStudentsAgeAvgWithStream() {
        return ResponseEntity.ok(studentService.getStudentsAgeAvgWithStream());
    }

    @GetMapping("/last-students")
    public ResponseEntity<List<StudentsList>> getLastStudentsById(@RequestParam Integer limit) {
        return ResponseEntity.ok(studentService.getLastStudentsById(limit));
    }

    @GetMapping("/name-starts-with")
    public ResponseEntity<List<String>> getStudentsByNameStartsWith(@RequestParam String letter) {
        return ResponseEntity.ok(studentService.getStudentsByNameStartsWith(letter));
    }

    @GetMapping("/print-students-name")
    public  void getStudentsName(){
      studentService.getStudentsName();
    }

    @GetMapping("/print-sync-students-name")
    public  void getSyncStudentsName(){
        studentService.getSyncStudentsName();
    }



    // @GetMapping("/age") //GET http://localhost:8080/students/2
    // public ResponseEntity<List<Student>> getStudentsByAge(@RequestParam Integer age){
    //    if (age<7||age>60){
    //        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //   }
    //  return ResponseEntity.ok(studentService.getStudentsByAge(age));
}


