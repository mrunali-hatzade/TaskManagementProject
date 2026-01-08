package com.taskmanagement.taskmanagementproject.Service;

import com.taskmanagement.taskmanagementproject.DTO.IssueDTO;
import com.taskmanagement.taskmanagementproject.Entity.Issue;
import com.taskmanagement.taskmanagementproject.Entity.IssueComment;
import com.taskmanagement.taskmanagementproject.Enum.IssuePriority;
import com.taskmanagement.taskmanagementproject.Enum.IssueStatus;
import com.taskmanagement.taskmanagementproject.Repository.EpicRepository;
import com.taskmanagement.taskmanagementproject.Repository.IssueCommentRepository;
import com.taskmanagement.taskmanagementproject.Repository.IssueRepository;
import com.taskmanagement.taskmanagementproject.Repository.SprintRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class IssueService {
    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private IssueCommentRepository issueCommentRepo;

    @Autowired
    private SprintRepository sprintRepo;

    @Autowired
    private EpicRepository epicRepo;

    private String generateKey(Long id) {
        return "PROJECT - " + id;
    }

    @Transactional
    public IssueDTO createIssue(IssueDTO dto) {
        Issue issue = new Issue();

        issue.setIssueTitle(dto.getIssueTitle());
        issue.setIssueType(dto.getIssueType());
        issue.setIssueKey("PROJECT - " + issue.getId());
        issue.setAssigneeEmail(dto.getAssigneeEmail());
        issue.setRepoterEmail(dto.getReporterEmail());
        issue.setCreatedAt(dto.getCreatedAt());
        issue.setDescription(dto.getDescription());
        issue.setPriority(IssuePriority.LOW);
        //issue.setSprintId(dto.getSprintId());
        issue.setStatus(IssueStatus.OPEN);
        //issue.setEpicId(dto.getEpicId());
        issue.setUpdateAt(dto.getUpdatedAt());
        issue.setDueDate(dto.getDueDate());

        if (dto.getEpicId() != null) {
            epicRepo.findById(dto.getEpicId()).orElseThrow(() -> new RuntimeException("EpicId not found"));
            issue.setEpicId(dto.getEpicId());
        }
        if (dto.getSprintId() != null) {
            sprintRepo.findById(dto.getSprintId()).orElseThrow(() -> new RuntimeException("SprintId not found"));
            issue.setSprintId(dto.getSprintId());
        }
        issueRepo.save(issue);

        return toDTO(issue);

    }

    public IssueDTO getIssue(Long id) {
        Issue issue = issueRepo.findById(id).orElseThrow(() -> new RuntimeException("Issue not found"));
        return toDTO(issue);

    }
    @Transactional
    public IssueDTO updateIssueStatus(Long id, IssueStatus status,String performBy) {

        Issue  issue = issueRepo.findById(id).orElseThrow(() -> new RuntimeException("Issue not found"));

        IssueStatus newStatus;
        try{
            newStatus = IssueStatus.valueOf(String.valueOf(status).toUpperCase().trim());
        }catch(Exception e){
            throw new RuntimeException("Invalid Status"+ status);
        }
        issue.setStatus(newStatus);
        issueRepo.save(issue);

        IssueComment comment = new IssueComment();
        comment.setIssueId(id);
        comment.setAuthorEmail(performBy);
        comment.setBody("Status changed to :"+status);
        return toDTO(issue);

    }

    @Transactional
    public IssueComment addComment(Long issueId, String authorEmail, String body) {

        issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));

        IssueComment comment = new IssueComment();
        comment.setIssueId(issueId);
        comment.setAuthorEmail(authorEmail);
        comment.setBody(body);

        return issueCommentRepo.save(comment);

    }

    public List<IssueDTO>search(Map<String,String> filter) {
        if (filter.containsKey("assignee")) {
            return issueRepo.findByAssigneeEmail(filter.get("assignee")).stream().map(this::toDTO).collect(Collectors.toList());

        }
        if (filter.containsKey("status")) {
            String statusStr = filter.get("status");
            IssueStatus status;

            try {
                status = IssueStatus.valueOf(statusStr.toUpperCase().trim());

            } catch (Exception e) {
                throw new RuntimeException("Invalid Status : " + statusStr + "| Allowed" + Arrays.toString(IssueStatus.values()));

            }
            return issueRepo.findByIssueStatus(status).stream().map(this::toDTO).collect(Collectors.toList());

        }
        if (filter.containsKey("sprintId")) {
            Long sprintId = Long.valueOf(filter.get("sprint"));
            return issueRepo.findBySprintId(sprintId).stream().map(this::toDTO).collect(Collectors.toList());

        }
        return issueRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private IssueDTO toDTO(Issue issue) {
        IssueDTO dto = new IssueDTO();

        dto.setIssueTitle(issue.getIssueTitle());
        dto.setDescription(issue.getDescription());
        dto.setIssueKey(issue.getIssueKey());
        dto.setCreatedAt(issue.getCreatedAt());
        dto.setIssuePriority(issue.getPriority());
        dto.setIssueStatus(issue.getStatus());
        dto.setIssueType(issue.getIssuetype());
        dto.setAssigneeEmail(issue.getAssigneeEmail());
        dto.setReporterEmail(issue.getRepoterEmail());
        dto.setUpdatedAt(issue.getUpdateAt());
        dto.setEpicId(issue.getEpicId());
        dto.setSprintId(issue.getSprintId());

        return dto;

    }
}