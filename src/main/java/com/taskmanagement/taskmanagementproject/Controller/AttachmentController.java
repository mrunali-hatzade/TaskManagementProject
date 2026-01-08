package com.taskmanagement.taskmanagementproject.Controller;

import com.cloudinary.Cloudinary;
import com.taskmanagement.taskmanagementproject.Entity.Attachment;
import com.taskmanagement.taskmanagementproject.Service.AttachmentService;
import jakarta.validation.ValidationException;
import org.apache.coyote.Request;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Configuration
@RequestMapping("/api/attachments")

public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/uplodFile/{issueId}")
    public ResponseEntity<Attachment>uploadFile(@PathVariable Long issueId,
                                                @RequestParam MultipartFile file,
                                                @RequestParam String uploadBy) throws IOException,FileUploadException, ValidationException {
        return ResponseEntity.ok(attachmentService.uploadFile(issueId,file,uploadBy));
    }
    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Void>downloadFile(@PathVariable Long id) throws IOException {
            Attachment attachment=attachmentService.getFileById(id);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, attachment.getStoragePath())
                    .build();
    }
    @GetMapping("/downloadFile/stream/{id}")
    public ResponseEntity<Resource> downloadFileForSystem(@PathVariable Long id) throws Exception {
        Attachment attach= attachmentService.getFileById(id);
        URL url = new URL(attach.getStoragePath());
        InputStream is = url.openStream();
        InputStreamResource resource = new InputStreamResource(is);
        return ResponseEntity.ok().header(HttpHeaders.LOCATION, "attachment;FileName=\""+attach.getFileName()+"\"")
                .contentType(MediaType.parseMediaType(attach.getContentType()))
                .body(resource);
    }
    @DeleteMapping("/deleteFile/{id}")
    public ResponseEntity<String>deletefile(@PathVariable Long id) throws FileUploadException, IOException {
        attachmentService.deleteFile(id);
        return ResponseEntity.ok("Attachment deleted successfully");
    }



}
