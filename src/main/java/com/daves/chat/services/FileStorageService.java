package com.daves.chat.services;

import com.daves.chat.interfaces.StorageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileStorageService implements StorageService {
    private final String mediaExtension = ".jpeg";
    /**
     * Change the pathname according to your system
     *
     * @param file
     */

    @Override
    public void store(MultipartFile file) {
        File folder = new File("/Users/anigrigoryan/Desktop/FileStorage");
        Path pathToSaveOn = FileSystems.getDefault().getPath(folder.getAbsolutePath() + "/" + file.getOriginalFilename());
        try {
            file.transferTo(pathToSaveOn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<ByteArrayResource> getFileWithName(String nameWithExtension) throws IOException {
        String folderPath = "/Users/anigrigoryan/Desktop/FileStorage";
        File folder = new File(folderPath);
        String filePath = folder.getAbsolutePath() + "/" + nameWithExtension;

        File file = new File(filePath);
        Path resultPath = Paths.get(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img" + mediaExtension);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(resultPath));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
