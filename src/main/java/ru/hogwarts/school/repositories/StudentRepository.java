package ru.hogwarts.school.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Student;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findStudentsByAgeBetween(int minAge, int maxAge);

    List<Student> findStudentsByFacultyId(long facultyId);
    @Query(value = "SELECT COUNT(*) FROM students", nativeQuery = true)
    int getStudentsCount();

    @Query(value = "SELECT AVG(age) FROM students", nativeQuery = true)
    Double getStudentsAgeAvg();

    @Query(value = "SELECT * FROM students ORDER BY id DESC LIMIT :limit", nativeQuery = true)
    List <StudentsList> getLastStudentsById(@Param("limit") int limit);
}