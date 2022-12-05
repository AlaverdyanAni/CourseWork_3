package ru.hogwarts.school.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import nonapi.io.github.classgraph.json.Id;
import java.util.List;
import java.util.Objects;

@Entity(name="Faculties")
public class Faculty {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String colour;

    @OneToMany(mappedBy = "faculty")
    private List <Student> students;

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.colour = colour;
    }

    public Faculty() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id.equals(faculty.id) && name.equals(faculty.name) && Objects.equals(colour, faculty.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, colour);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", colour='" + colour + '\'' +
                '}';
    }
}
