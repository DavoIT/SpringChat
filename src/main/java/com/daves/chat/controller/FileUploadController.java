package com.daves.chat.controller;

import com.daves.chat.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
    @Autowired
    FileStorageService storageService;

    @PostMapping("/file")
    public void uploadFile(@RequestParam(name = "file") MultipartFile file) {
        storageService.store(file);
    }

}
