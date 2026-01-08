package com.taskmanagement.taskmanagementproject.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.taskmanagement.taskmanagementproject.Entity.Attachment;
import com.taskmanagement.taskmanagementproject.Repository.AttachmentRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepo;

    @Autowired
    private Cloudinary cloudinary;

    // ✅ Upload file
    public Attachment uploadFile(Long issue, MultipartFile file, String uploadBy) throws IOException {
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("resource_type", "auto");

            Map uploadResults = cloudinary.uploader()
                    .upload(file.getBytes(), options);

            Attachment attach = new Attachment();
            attach.setIssuesId(issue);
            attach.setFileName(file.getOriginalFilename());
            attach.setFileSize(file.getSize());
            attach.setContentType(file.getContentType());
            attach.setStoragePath(uploadResults.get("secure_url").toString());
            attach.setPublicId(uploadResults.get("public_id").toString());
            attach.setUploadedBy(uploadBy);

            return attachmentRepo.save(attach);

        } catch (Exception e) {
            throw new FileUploadException("File upload failed", e);
        }
    }

    // ✅ Get file by ID
    public Attachment getFileById(Long id) {
        return attachmentRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Attachment not found with id: " + id));
    }

    // ✅ Delete file
    public void deleteFile(Long id) throws IOException {
        Attachment attachment = getFileById(id);

        // Delete from Cloudinary
        cloudinary.uploader().destroy(
                attachment.getPublicId(),
                ObjectUtils.emptyMap()
        );

        // Delete from DB
        attachmentRepo.delete(attachment);
    }
}
