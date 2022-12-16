package ru.hogwarts.school.model;
import javax.persistence.*;

@Entity
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;
    private String filePath; //путь файла на диске, куда будем сохранять оригинал файла
    private long fileSize; //размер файла
    private String mediaType; //тип файла
    @Lob
    private byte[]data; //массив байт аватарки в уменшенном размере(хранить будем в БД)
    @OneToOne
   private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Student getStudent(){
        return student;
    }
    public  void setStudent(Student student){
        this.student=student;
    }
}
