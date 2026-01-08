package com.taskmanagement.taskmanagementproject.Controller;

import com.taskmanagement.taskmanagementproject.Entity.Issue;
import com.taskmanagement.taskmanagementproject.Service.BackLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backlogs")

public class BackLogController {
    @Autowired
    private BackLogService backLogService;

    @GetMapping("/{projectId}")
    public ResponseEntity<List<Issue>> getBackLog(@PathVariable Long projectId ){
        return ResponseEntity.ok(backLogService.getBackLog(projectId));
    }
    @PostMapping("/{projectId}/record")
    public ResponseEntity<String>record(@PathVariable Long projectId, @RequestBody List <Long>orderIssueId ){
        backLogService.recordBackLog(projectId,orderIssueId);
        return ResponseEntity.ok("BackLog Recorded");
    }
    @PostMapping("/add_to-sprint/{issueId}/{sprintId}")
    public ResponseEntity<Issue> addIssueToSprint(@PathVariable Long issueId, @PathVariable Long sprintId){
        return ResponseEntity.ok(backLogService.addIssueToSprint(issueId,sprintId));
    }
    @GetMapping("/{projectId}/hierarchy")
    public ResponseEntity<Map<String, Object>> getHierarchy (@PathVariable Long projectId) {
        return ResponseEntity.ok(backLogService.getBackLogHierarchy(projectId));
    }
}
