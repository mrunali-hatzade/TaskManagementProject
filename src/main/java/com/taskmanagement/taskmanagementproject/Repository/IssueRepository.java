package com.taskmanagement.taskmanagementproject.Repository;

import com.taskmanagement.taskmanagementproject.Entity.Issue;
import com.taskmanagement.taskmanagementproject.Enum.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface IssueRepository extends JpaRepository<Issue,Long> {
    Optional<Issue> findByIssueKey(String issueKey);
    Optional<Issue> findById(Long id);
    List<Issue> findByAssigneeEmail(String assigneeEmail);
    List<Issue> findBySprintId(Long sprintId);
    List<Issue> findByIssueStatus(IssueStatus issueStatus);
    List<Issue> findByProjectIdAndSprintIdIsNullOrderByBackLogPosition(Long projectId);



}
