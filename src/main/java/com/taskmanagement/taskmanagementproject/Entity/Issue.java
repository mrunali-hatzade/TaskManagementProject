package com.taskmanagement.taskmanagementproject.Entity;

import com.taskmanagement.taskmanagementproject.Enum.IssuePriority;
import com.taskmanagement.taskmanagementproject.Enum.IssueStatus;
import com.taskmanagement.taskmanagementproject.Enum.IssueType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique =true, nullable = false)
    private String issueKey;
    @Column(nullable = false)
    private String issueTitle;

    @Column(length =10000)
    private String description;

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    private IssuePriority priority;

    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    private String assigneeEmail;
    private String repoterEmail;

    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime updateAt=LocalDateTime.now();

    private LocalDateTime dueDate;

    private Long sprintId;
    private Long epicId;

    private Long sourceIssueID;
    private Integer backlogPosition;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssueType getIssuetype() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public void setAssigneeEmail(String assigneeEmail) {
        this.assigneeEmail = assigneeEmail;
    }

    public String getRepoterName() {
        return repoterEmail;
    }

    public void setRepoteEmail(String repoterEmail) {
        this.repoterEmail = repoterEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Long getEpicId() {
        return epicId;
    }

    public void setEpicId(Long epicId) {
        this.epicId = epicId;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public String getRepoterEmail() {
        return repoterEmail;
    }

    public void setRepoterEmail(String repoterEmail) {
        this.repoterEmail = repoterEmail;
    }

    public Long getSourceIssueID() {
        return sourceIssueID;
    }

    public void setSourceIssueID(Long sourceIssueID) {
        this.sourceIssueID = sourceIssueID;
    }

    public Integer getBacklogPosition() {
        return backlogPosition;
    }

    public void setBacklogPosition(Integer backlogPosition) {
        this.backlogPosition = backlogPosition;
    }
}
