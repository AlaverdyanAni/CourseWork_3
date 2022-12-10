package ru.hogwarts.school.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${students.avatar.dir.path}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository,StudentService studentService){
        this.avatarRepository=avatarRepository;
        this.studentService=studentService;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException{
        Student student=studentService.readStudent(studentId);

        Path filePath= Path.of(avatarsDir,studentId+"."+getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try(
                InputStream is=avatarFile.getInputStream();
                OutputStream os=Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis=new BufferedInputStream(is,1024);
                BufferedOutputStream bos=new BufferedOutputStream(os,1024);
                ) {
            bis.transferTo(bos);
        }
        Avatar avatar=findAvatarByStudentId(studentId);
                avatar.setStudent(student);
                avatar.setFilePath(filePath.toString());
                avatar.setFileSize(avatarFile.getSize());
                avatar.setMediaType(avatarFile.getContentType());
                avatar.setData(avatarFile.getBytes());
                avatarRepository.save(avatar);
    }
    public Avatar findAvatarByStudentId(Long studentId) {
        return avatarRepository.findAvatarByStudentId(studentId).orElse(new Avatar());
    }

    private String getExtensions(String fileName){
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    private byte[] generateAvatarPreview(Path filePath) throws IOException {
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getHeight() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();
            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()),baos);
            return baos.toByteArray();
        }
    }
}