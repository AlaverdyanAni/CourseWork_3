package ru.hogwarts.school;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers= FacultyController.class)
public class CourseWork_SchoolApplicationMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private FacultyService facultyService;
    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void createFacultyTest() throws Exception {
        final long id = 5L;
        final String name = "TestFaculty";
        final String colour = "testColour";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("colour", colour);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColour(colour);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.colour").value(colour));
    }
    @Test
    public void readFacultyTest() throws Exception {
        final long id = 5L;
        final String name = "TestFaculty";
        final String colour = "testColour";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("colour", colour);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColour(colour);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}",id)
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.colour").value(colour));
    }

    @Test
    public void updateFacultyTest() throws Exception {
        final long id = 5L;
        final String name = "TestFaculty";
        final String colour = "testColour";
        final String newName = "TestNewFaculty";
        final String newColour = "testNewColour";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id",id);
        facultyObject.put("name",newName);
        facultyObject.put("colour", newColour);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColour(colour);

        Faculty updateFaculty=new Faculty();
        updateFaculty.setId(id);
        updateFaculty.setName(newName);
        updateFaculty.setColour(newColour);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(updateFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.colour").value(newColour));
    }
    @Test
    public void deleteFacultyTest() throws Exception {
        final long id = 5L;
        final String name = "TestFaculty";
        final String colour = "testColour";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColour(colour);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(facultyRepository,atLeastOnce()).deleteById(id);
    }
    @Test
    public void getFacultiesTest() throws Exception {
        final long id1 = 5L;
        final String name1 = "TestFaculty1";
        final String colour1 = "testColour1";
        final long id2 = 6L;
        final String name2 = "TestFaculty2";
        final String colour2 = "testColour2";
        final long id3 = 7L;
        final String name3 = "TestFaculty3";
        final String colour3 = "testColour3";

        JSONObject facultyObject1 = new JSONObject();
        facultyObject1.put("name1", name1);
        facultyObject1.put("colour1", colour1);

        JSONObject facultyObject2 = new JSONObject();
        facultyObject2.put("name2", name2);
        facultyObject2.put("colour2", colour2);

        JSONObject facultyObject3 = new JSONObject();
        facultyObject3.put("name3", name3);
        facultyObject3.put("colour3", colour3);

        Faculty faculty1 = new Faculty();
        faculty1.setId(id1);
        faculty1.setName(name1);
        faculty1.setColour(colour1);

        Faculty faculty2 = new Faculty();
        faculty2.setId(id2);
        faculty2.setName(name2);
        faculty2.setColour(colour2);

        Faculty faculty3 = new Faculty();
        faculty3.setId(id3);
        faculty3.setName(name3);
        faculty3.setColour(colour3);

        when(facultyRepository.findAll()).thenReturn(List.of(faculty1,faculty2,faculty3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .content(facultyObject1.toString())
                        .content(facultyObject2.toString())
                        .content(facultyObject3.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty1,faculty2,faculty3))));
    }
    @Test
    public void findFacultiesByNameOrColour()throws Exception{
        final String text="testColour1";
        final long id1 = 5L;
        final String name1 = "TestFaculty1";
        final String colour1 = "testColour1";
        final long id2 = 6L;
        final String name2 = "TestFaculty2";
        final String colour2 = "testColour2";

        JSONObject facultyObject1 = new JSONObject();
        facultyObject1.put("id1",id1);
        facultyObject1.put("name1",name1);
        facultyObject1.put("colour1", colour1);

        JSONObject facultyObject2 = new JSONObject();
        facultyObject2.put("id2",id2);
        facultyObject2.put("name2",name2);
        facultyObject2.put("colour2", name2);

        Faculty faculty1=new Faculty();
        faculty1.setId(id1);
        faculty1.setName(name1);
        faculty1.setColour(colour1);

        Faculty faculty2=new Faculty();
        faculty1.setId(id2);
        faculty1.setName(name2);
        faculty1.setColour(colour2);

        when(facultyRepository.findFacultiesByNameIgnoreCaseOrColourIgnoreCase(text,text))
                .thenReturn(List.of(faculty1,faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/colour")
                .queryParam("text",text)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty1,faculty2))));

    }
}
