package ru.hogwarts.school.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;
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
import java.util.Collection;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
//@Profile("prodaction")
@Transactional
public class AvatarService {
    @Value("${students.avatar.dir.path}")

    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    Logger logger= LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(AvatarRepository avatarRepository,StudentService studentService){
        this.avatarRepository=avatarRepository;
        this.studentService=studentService;
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException{
        logger.debug("Loading  avatar for student with ID: {}",studentId);
        Student student=studentService.readStudent(studentId);

        Path filePath= Path.of(avatarsDir,studentId+"."+getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try(InputStream is=file.getInputStream();
            OutputStream os=Files.newOutputStream(filePath, CREATE_NEW);
            BufferedInputStream bis=new BufferedInputStream(is,1024);
            BufferedOutputStream bos=new BufferedOutputStream(os,1024);
            ){
            bis.transferTo(bos);
        }
        Avatar avatar=findAvatarByStudentId(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImagePreview(filePath));
        avatarRepository.save(avatar);
    }
    public Avatar findAvatarByStudentId(Long studentId) {
        logger.debug("Find  avatar by student ID: {}", studentId);
        return avatarRepository.findAvatarByStudentId(studentId).orElseThrow(()->new AvatarNotFoundException(studentId));
    }

    private String getExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()),baos);
            return baos.toByteArray();
        }
    }
    public ResponseEntity<Collection<Avatar>> findByPagination(int pageNumber, int pageSize){
        logger.debug("Find avatars by pagination for page: {}, size: {}", pageNumber, pageSize);
        PageRequest pageRequest=PageRequest.of(pageNumber-1, pageSize);

        Collection<Avatar> avatars=avatarRepository.findAll(pageRequest).getContent();
        if (avatars.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avatars);
    }
}