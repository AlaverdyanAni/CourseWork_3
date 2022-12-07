package ru.hogwarts.school.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping // POST http:localhost:8080/faculty
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("{id}") // GET http://localhost:8080/faculty/1
    public ResponseEntity<Faculty> readFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.readFaculty(id);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping // PUT http"//localhost:8080/faculty
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{id}") // DELETE http"//localhost:8080/faculty/1
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping // GET http"//localhost:8080/faculty
    public ResponseEntity<List<Faculty>> getFaculties() {
        return ResponseEntity.ok(facultyService.getFaculties());
    }

    @GetMapping( "/colour") //GET http"//localhost:8080/faculty/colour
    public ResponseEntity<List<Faculty>> findFacultiesByNameOrColour(@RequestParam(required = false) String text){
        List<Faculty>faculties=facultyService.findFacultiesByNameOrColour(text);
        if (faculties.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}/students") //GET http"//localhost:8080/3/students
    public ResponseEntity <List<Student>> findFacultyStudents(@PathVariable Long id){
        return ResponseEntity.ok(facultyService.findFacultyStudents(id));
    }


    //@GetMapping("/colour") // GET http"//localhost:8080/faculty?colour=
    //public ResponseEntity<List<Faculty>> getFacultiesByColour(@RequestParam String colour){
    //    if (colour==null && colour.isBlank()){
    //       return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //    }
    //  return ResponseEntity.ok(facultyService.getFacultiesByColour(colour));
    // }
}
