package com.student.AutomationPortal.controller;

import com.student.AutomationPortal.model.FileUploadResponse;
import com.student.AutomationPortal.serviceImpl.FileServiceImpl;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file")
public class FileUploadController {
    @PostMapping("")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file")MultipartFile multipartFile) throws IOException {
        String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        FileUploadResponse fileUploadResponse= new FileUploadResponse();
        fileUploadResponse.setFileName(multipartFile.getName());
        fileUploadResponse.setDownloadUri(new FileServiceImpl().saveFile(fileName, multipartFile));
        return new ResponseEntity<>(fileUploadResponse, HttpStatus.OK);
    }

    @GetMapping("/{fileNameWithRelativePath}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileNameWithRelativePath") String fileNameWithRelativePath){
        FileServiceImpl fileServiceImpl= new FileServiceImpl();
        Resource resource= null;
        try{
            resource= fileServiceImpl.getFile(fileNameWithRelativePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        if (resource==null)
            return new ResponseEntity<>("File not found "+ fileNameWithRelativePath, HttpStatus.NOT_FOUND);

        String contentType="application/oclet-stream";
        String headerValue="attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
