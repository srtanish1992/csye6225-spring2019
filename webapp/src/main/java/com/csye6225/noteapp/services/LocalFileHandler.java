package com.csye6225.noteapp.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
@Profile("default")
public class LocalFileHandler implements FileHandler {

    private String tmpFolder = "tmp";

    @Override
    public String uploadFile(MultipartFile multipartFile, String emailAddress) throws Exception {
        byte[] bytes = multipartFile.getBytes();
        Path path = Paths.get(tmpFolder, emailAddress);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        StringBuilder fileName = new StringBuilder(new Date().toString());
        fileName.append("-" + multipartFile.getOriginalFilename());
        path = Paths.get(tmpFolder, emailAddress, fileName.toString().replace(" ", "_"));
        Files.write(path.toAbsolutePath(), bytes);
        return path.toString();
    }

    @Override
    public String deleteFile(String fileLocation, String emailAddress) throws Exception {
        File file = new File(fileLocation);
        if (file.delete()) {
            return "Successfully deleted";
        }
        return null;
    }
}
