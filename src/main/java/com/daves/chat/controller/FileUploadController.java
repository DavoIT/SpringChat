package com.daves.chat.controller;

import com.daves.chat.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class FileUploadController {
    @Autowired
    FileStorageService storageService;

    @PostMapping("/file")
    public void uploadFile(@RequestHeader Map<String, String> headers, @RequestParam(name = "file") MultipartFile file) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });
        storageService.store(file);
    }

    @GetMapping("/file")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value = "name") String nameWithExtension) throws IOException {
        return storageService.getFileWithName(nameWithExtension);
    }

}
