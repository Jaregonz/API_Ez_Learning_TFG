package com.es.API_REST_Ez_Learning.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path root = Paths.get("uploads");

    public FileStorageService() throws IOException {
        Files.createDirectories(root);
    }

    public String saveFile(MultipartFile file, String subdirectory) {
        try {
            Path subDirPath = root.resolve(subdirectory);
            Files.createDirectories(subDirPath);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path filePath = subDirPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + subdirectory + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar el archivo", e);
        }
    }
}
