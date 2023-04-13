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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/file")
public class FileUploadController {
    @PostMapping("")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file")MultipartFile multipartFile) throws IOException {
        String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        FileUploadResponse fileUploadResponse= new FileUploadResponse();
        fileUploadResponse.setFileName(fileName);
        fileUploadResponse.setDownloadUri(new FileServiceImpl().saveFile(fileName, multipartFile));
        return new ResponseEntity<>(fileUploadResponse, HttpStatus.OK);
    }

    @GetMapping("/{fileNameWithRelativePath}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName, @RequestParam("localFilePath") String filePath) throws IOException {
        FileServiceImpl fileServiceImpl= new FileServiceImpl();
        Resource resource= null;
        try{
            resource= fileServiceImpl.getFile(fileName);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        if (resource==null)
            return new ResponseEntity<>("File not found "+ fileName, HttpStatus.NOT_FOUND);

        String contentType="application/oclet-stream";
        String headerValue="attachment; filename=\"" + resource.getFilename() + "\"";
        /************************************************************************/
        String localFileBaseLocation="c:/temp";
        Files.createDirectories(Paths.get(localFileBaseLocation));
        File outputFile= new File(localFileBaseLocation, fileName);

        if(outputFile.exists())
            outputFile.delete();
        OutputStream outputStream=new FileOutputStream(outputFile);
        InputStream is= resource.getInputStream();
        try{
            byte[] buffer= new byte[1024];
            int length;

            while((length=is.read(buffer))>0){
                outputStream.write(buffer,0,length);
            }
        }catch (Exception e){
            System.out.println("Error writing file "+ outputFile.getAbsolutePath());
        }finally {
            {
                if(outputStream!=null){
                    outputStream.close();
                }
                is.close();
            }
        }
        /************************************************************************/
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
