package com.taskmanagement.taskmanagementproject.Repository;

import com.taskmanagement.taskmanagementproject.Entity.Issue;
import com.taskmanagement.taskmanagementproject.Entity.IssueComment;
import com.taskmanagement.taskmanagementproject.Entity.Sprint;
import com.taskmanagement.taskmanagementproject.Enum.IssueStatus;
import com.taskmanagement.taskmanagementproject.Enum.SprintState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface SprintRepository extends JpaRepository<Sprint,Long> {

    /*Optional<Issue>findByIssueKey(String issueKey);
    List<Issue> findByAssigneeEmail(Sprint assigneeEmail);
    List<Issue>findBySprintId(Long sprintId);
    List<Issue> findByIssueStatus(IssueStatus issueStatus);
*/
    List<Sprint> findByProjectId(Long projectId);
    List <Sprint> findByState(SprintState state);
}
