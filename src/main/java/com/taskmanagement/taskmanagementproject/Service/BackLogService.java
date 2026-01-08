package com.taskmanagement.taskmanagementproject.Service;

import com.taskmanagement.taskmanagementproject.Entity.Issue;
import com.taskmanagement.taskmanagementproject.Entity.Sprint;
import com.taskmanagement.taskmanagementproject.Enum.IssueType;
import com.taskmanagement.taskmanagementproject.Enum.SprintState;
import com.taskmanagement.taskmanagementproject.Repository.IssueRepository;
import com.taskmanagement.taskmanagementproject.Repository.SprintRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class BackLogService {
    @Autowired
    private IssueRepository issueRepo;

    @Autowired
    private SprintRepository sprintRepo;

    public List<Issue> getBackLog(Long projectId) {
        if (projectId == null) {
            return issueRepo.findByProjectIdAndSprintIdIsNullOrderByBackLogPosition(null);
        }
        return issueRepo.findByProjectIdAndSprintIdIsNullOrderByBackLogPosition(projectId);

    }

    @Transactional
    public void recordBackLog(Long projectId, List<Long> orderIssueId) {
        int pos=0;
        for (Long issueId : orderIssueId) {
            Issue issue= issueRepo.findById(issueId).orElseThrow(()-> new RuntimeException("issueId not found"));
            issue.setBacklogPosition(pos++);
            issueRepo.save(issue);
        }

    }
    @Transactional
    public Issue addIssueToSprint(Long sprintId, Long issueId) {
        Issue issue= issueRepo.findById(issueId).orElseThrow(()-> new RuntimeException("issueId not found"));
        Sprint sprint= sprintRepo.findById(sprintId).orElseThrow(()-> new RuntimeException("sprintId not found"));

        SprintState sprintState= sprint.getState();
        if (sprintState!=SprintState.PLANNED && sprintState != SprintState.ACTIVE) {
            throw new RuntimeException("can not add issue to sprint in state "+ sprintState);

        }
        issue.setSprintId(sprintId);
        issue.setBacklogPosition(null);
        return issueRepo.save(issue);
    }
    public Map<String,Object>getBackLogHierarchy(Long projectId){
        List <Issue>backlog=getBackLog(projectId);
        Map<Long, Map<String, Object>> epicMap = new LinkedHashMap<>();

        for (Issue i:backlog){
            //if(i.getIssueType() !=null && "EPICS".equalsIgnoreCase(i.getIssueType().name()){
            if (i.getIssueType()== IssueType.EPICS){

                Map<String,Object> data=new LinkedHashMap<>();

                data.put("epic",i);
                data.put("stories",new ArrayList<Issue>());
                data.put("subtasks", new HashMap<Long,List<Issue>>());
                epicMap.put(i.getId(),data);

            }
        }
        for(Issue i:backlog){
            if(i.getIssueType()==IssueType.STORIES && i.getEpicId()!=null){

                Map<String,Object> epicData= (Map<String, Object>) epicMap.get(i.getEpicId());
                if(epicData != null){
                    List<Issue>stories=(List<Issue>)epicData.get("stories");
                    stories.add(i);
                }
            }
        }
        for(Issue i:backlog){
            if(i.getIssueType()==IssueType.SUBTASKS && i.getSourceIssueID()!=null) {

                Long sourceIssueId = i.getSourceIssueID();

                for (Map<String, Object>epicData : epicMap.values()) {

                    List<Issue> stories = (List<Issue>) epicData.get("stories");

                    for (Issue story : stories) {

                        if (story.getId().equals(sourceIssueId)) {

                            Map<Long, List<Issue>> subtasksMaps = (Map<Long, List<Issue>>) epicData.get("subtasks");

                            subtasksMaps.computeIfAbsent(sourceIssueId, k -> new ArrayList<>()).add(i);
                            break;

                        }
                    }
                }
            }
        }
                    return Collections.singletonMap("epics",epicMap.values());
    }
}