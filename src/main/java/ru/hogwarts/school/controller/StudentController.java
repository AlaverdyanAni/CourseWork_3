package ru.hogwarts.school.controller;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService,
                             AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }
    @PostMapping //POST http://localhost:8080/student
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("{id}") //GET http://localhost:8080/student/2
    public ResponseEntity<Student> readStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.readStudent(id));
    }

    @PutMapping //PUT http://localhost:8080/student
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("{id}") //Delete http://localhost:8080/student/2
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/all") //GET http://localhost:8080/student/2
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping (params = {"minAge","maxAge"})//GET http://localhost:8080/student?minAge=18&maxAge=20
    public ResponseEntity<List<Student>> findStudentsByAgeBetween(@RequestParam int minAge,
                                                                  @RequestParam int maxAge) {
        return ResponseEntity.ok(studentService.findStudentsByAgeBetween(minAge, maxAge));
    }

    @GetMapping("/{id}/faculty") //GET http://localhost:8080/student/1/faculty
    public  ResponseEntity <Faculty> findStudentFaculty(@PathVariable Long id){
        return ResponseEntity.ok(studentService.findStudentFaculty(id));
    }

    @PostMapping(value  ="{id}/avatar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id,
                                               @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big!");
        }
        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long studentId) {
        Avatar avatar = avatarService.findAvatarByStudentId(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/avatar-from-file")
            public void downloadAvatar(@PathVariable Long studentId, HttpServletResponse response) throws IOException{
        Avatar avatar=avatarService.findAvatarByStudentId(studentId);
        Path path= Path.of(avatar.getFilePath());
        try(
                InputStream is= Files.newInputStream(path);
                OutputStream os=response.getOutputStream()){
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int)avatar.getFileSize());
            is.transferTo(os);
        }
    }
}


    // @GetMapping("/age") //GET http://localhost:8080/students/2
    // public ResponseEntity<List<Student>> getStudentsByAge(@RequestParam Integer age){
    //    if (age<7||age>60){
    //        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //   }
    //  return ResponseEntity.ok(studentService.getStudentsByAge(age));
    // }


