package ru.hogwarts.school.controller;
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

    @PostMapping // POST http:localhost:8080/faculties
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @GetMapping("{id}") // GET http://localhost:8080/faculties/1
    public ResponseEntity<Faculty> readFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.readFaculty(id));
    }

    @PutMapping("/{id}") // PUT http"//localhost:8080/faculties
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id,@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.updateFaculty(id,faculty));
    }

    @DeleteMapping("{id}") // DELETE http"//localhost:8080/faculties/1
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/all") // GET http"//localhost:8080/faculty/all
    public ResponseEntity<List<Faculty>> getFaculties() {
        return ResponseEntity.ok(facultyService.getFaculties());
    }

    @GetMapping(value = "/colour",params = "text") //GET http"//localhost:8080/faculties/colour?text=
    public ResponseEntity<List<Faculty>> findFacultiesByNameOrColour(@RequestParam String text){
        return ResponseEntity.ok(facultyService.findFacultiesByNameOrColour(text));
    }

    @GetMapping("/{id}/students") //GET http"//localhost:8080/3/students
    public ResponseEntity <List<Student>> findFacultyStudents(@PathVariable Long id){
        return ResponseEntity.ok(facultyService.findFacultyStudents(id));
    }


    //@GetMapping("/colour") // GET http"//localhost:8080/faculties/colour?colour=
    //public ResponseEntity<List<Faculty>> getFacultiesByColour(@RequestParam String colour){
    //    if (colour==null && colour.isBlank()){
    //       return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //    }
    //  return ResponseEntity.ok(facultyService.getFacultiesByColour(colour));
    // }
}
