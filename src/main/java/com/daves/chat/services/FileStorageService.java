package com.daves.chat.services;

import com.daves.chat.interfaces.StorageService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Component
public class FileStorageService implements StorageService {

    /**
     * Change the pathname according to your system
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

    @Override
    public void deleteAll() {

    }
}
