package com.neo.buysell.model.service;

import com.neo.buysell.model.dto.ImageDTO;
import com.neo.buysell.model.exception.particular.RuntimeIOException;
import com.neo.buysell.model.util.Paths;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileService {

    public ImageDTO readImage(String path) {
        File file = new File(path);
        byte[] bytes;
        try (FileInputStream fis = new FileInputStream(file)) {
            bytes = fis.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        MediaType mediaType = ImageDTO.toMediatype(path);
        return new ImageDTO(bytes, mediaType, path);
    }

    public ImageDTO writeImage(MultipartFile file, String directory) {
        byte[] bytes;
        String fileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().substring(0, 4);
        Path path = Path.of(directory + uuid + fileName);
        try {
            bytes = file.getBytes();
            Files.write(path, bytes);
        } catch (IOException ex) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        MediaType mediatype = ImageDTO.toMediatype(fileName);
        return new ImageDTO(bytes, mediatype, path.toString());
    }

    public void removeImage(String path) {
        if (!path.contains(Paths.STANDARD_IMAGE_DIRECTORY)) {
            try {
                Files.deleteIfExists(Path.of(path));
            } catch (IOException e) {
                throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostConstruct
    private void createDirectories() {
        Path imageDirectory = Path.of(Paths.IMAGE_DIRECTORY);
        Path avatarDirectory = Path.of(Paths.AVATAR_DIRECTORY);
        try {
            Files.createDirectories(imageDirectory);
            Files.createDirectories(avatarDirectory);
        } catch (IOException ex) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


