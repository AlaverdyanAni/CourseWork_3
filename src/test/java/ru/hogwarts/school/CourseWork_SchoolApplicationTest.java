package ru.hogwarts.school;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseWork_SchoolApplicationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads()throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void crateStudentTest() throws Exception{
        Student student=new Student();
        student.setName("name");
        student.setAge(11);
        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:"+port+"/student",student,String.class))
                .isNotNull();
    }

    @Test
    public void readStudentTest() throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student",String.class))
                .isNotNull();
    }

}
