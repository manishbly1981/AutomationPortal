package com.student.AutomationPortal.serviceImpl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileServiceImpl {
    Path dirPath= Paths.get("file-upload");
    int fileCodeLength=12;
    private Path foundFile;

    public String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        if(!Files.exists(dirPath))
            Files.createDirectories(dirPath);

        String fileCode= RandomStringUtils.randomAlphabetic(fileCodeLength);
        try(InputStream inputStream= multipartFile.getInputStream()){
            Files.copy(inputStream, dirPath.resolve(fileCode+"-"+fileName), StandardCopyOption.REPLACE_EXISTING);
        }catch(Exception e){
            throw new IOException("Could not save the file "+ fileName, e);
        }
        return fileCode;
    }

    public Resource getFile(String fileCode) throws IOException {
        Files.list(dirPath).forEach(file->{
            if(file.getFileName().toString().startsWith(fileCode)){
                foundFile= file;
                return;
            }
        });
        if(foundFile!=null){
            return new UrlResource(foundFile.toUri());
        }
        return null;
    }
}
