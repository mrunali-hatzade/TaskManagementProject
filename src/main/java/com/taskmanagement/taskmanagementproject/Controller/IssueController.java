package com.taskmanagement.taskmanagementproject.Controller;

import com.taskmanagement.taskmanagementproject.DTO.IssueDTO;
import com.taskmanagement.taskmanagementproject.Entity.IssueComment;
import com.taskmanagement.taskmanagementproject.Enum.IssueStatus;
import com.taskmanagement.taskmanagementproject.Service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Issues")
@RequiredArgsConstructor

public class IssueController {
    @Autowired
    private IssueService issueService;

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueDTO dto) {
        return ResponseEntity.ok(issueService.createIssue(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDTO> getIssue(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.getIssue(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<IssueDTO> updateStatus(@PathVariable Long id, @RequestBody IssueStatus Status, @RequestParam String performBy) {
        return ResponseEntity.ok(issueService.updateIssueStatus(id, Status, performBy));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<IssueComment>addComment(@PathVariable Long id, @RequestParam String author, @RequestParam String body) {
        return ResponseEntity.ok(issueService.addComment(id, author, body));

    }
    @GetMapping("/search")
    public ResponseEntity<IssueDTO> search(@RequestParam Map<String,String>filter) {
        return ResponseEntity.ok((IssueDTO) issueService.search(filter));
    }

}
