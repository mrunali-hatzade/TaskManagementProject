package com.taskmanagement.taskmanagementproject.Service;

import com.taskmanagement.taskmanagementproject.Entity.Issue;
import com.taskmanagement.taskmanagementproject.Entity.Sprint;
import com.taskmanagement.taskmanagementproject.Enum.IssueStatus;
import com.taskmanagement.taskmanagementproject.Enum.SprintState;
import com.taskmanagement.taskmanagementproject.Repository.IssueRepository;
import com.taskmanagement.taskmanagementproject.Repository.SprintRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SprintService {
    @Autowired
    private SprintRepository sprintRepo;
    @Autowired
    private IssueRepository issueRepos;

    public Sprint createSprint(Sprint sprint) {
        sprint.setState(SprintState.PLANNED);
        return sprintRepo.save(sprint);
    }

    @Transactional
    public Issue assignIssueToSprint(Long sprintId, Long issueId) {
        Sprint  sprint = sprintRepo.findById(sprintId).orElseThrow(()-> new RuntimeException("sprint not found"));
        Issue issue=issueRepos.findById(issueId).orElseThrow(()-> new RuntimeException("issue not found"));

        if(sprint.getState()!= SprintState.COMPLETE){
            throw new  RuntimeException("Can not add issue to completed sprint. ");
        }
        issue.setSprintId(sprintId);
        return  issueRepos.save(issue);
    }
    @Transactional
    public Sprint startSprint(Long sprintId) {
        Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(() -> new RuntimeException("sprint not found"));

        if (sprint.getState() != SprintState.PLANNED) {
            throw new RuntimeException("Only planned sprints can be started. ");
        }
        if (sprint.getStartDate() == null) {
            sprint.setStartDate(LocalDateTime.now());
        }
        return sprintRepo.save(sprint);
    }
    @Transactional
    public Sprint closeSprint(Long sprintId) {
        Sprint sprint= sprintRepo.findById(sprintId).orElseThrow(() -> new RuntimeException("sprint not found"));

        sprint.setState(SprintState.COMPLETE);
        if (sprint.getEndDate() ==null) {
            sprint.setEndDate(LocalDateTime.now());
        }
        List<Issue> issues=issueRepos.findBySprintId(sprintId);

        for (Issue issue: issues) {
            if(!issue.getStatus().name().equals(IssueStatus.DONE)){
                issue.setSprintId(null);
                issue.setBacklogPosition(0);
                issueRepos.save(issue);
            }
        }
        return  sprintRepo.save(sprint);
    }

    public Map<String,Object> getBurnDownDate(Long sprintId){
        Sprint sprint=sprintRepo.findById(sprintId).orElseThrow(() -> new RuntimeException("sprint not found"));

        LocalDateTime start=sprint.getStartDate();
        LocalDateTime end=sprint.getEndDate()!=null?
                sprint.getEndDate():LocalDateTime.now();
        List<Issue> issues=issueRepos.findBySprintId(sprintId);
        int totalTask=issues.size();

        Map<String,Object>burndown =new LinkedHashMap<>();
        LocalDateTime cursor= start;
        while(!cursor.isAfter(end)){
            long completed = issues.stream().filter(i->"DONE".equals(i.getStatus().name())).count();
       burndown.put(cursor.toString(),totalTask-(int)completed);
       cursor=cursor.plusDays(1);
        }
        Map<String, Object >response =new HashMap<>();
        response.put("sprintId",sprintId);
        response.put("startDate",start);
        response.put("endDate",end);
        response.put("burndown",burndown);
        return response;

}
}
