package com.taskmanagement.taskmanagementproject.Repository;

import com.taskmanagement.taskmanagementproject.Entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
    List<Attachment> findByIssuesId(Long issuesId);



}
