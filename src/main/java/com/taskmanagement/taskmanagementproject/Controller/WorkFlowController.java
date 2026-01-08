package com.taskmanagement.taskmanagementproject.Controller;

import com.taskmanagement.taskmanagementproject.Entity.WorkFlow;
import com.taskmanagement.taskmanagementproject.Entity.WorkFlowTransaction;
import com.taskmanagement.taskmanagementproject.Service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor

public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowService;

    @PatchMapping("/create")

    public ResponseEntity<WorkFlow> create(@RequestBody  WorkFlow wf) {
        return ResponseEntity.ok(workFlowService.createWorkFlow(wf));
    }

    @GetMapping("/list")
    public ResponseEntity<List<WorkFlow>>alllist() {
        return ResponseEntity.ok(workFlowService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkFlow> get(@PathVariable Long id) {
        return ResponseEntity.ok(workFlowService.getById(id));
    }
    @GetMapping("/{workFlowName}")
    public ResponseEntity<Optional<WorkFlow>> getByName(@PathVariable String workFlowName) {
        return ResponseEntity.ok(workFlowService.findByWorkFlowName(workFlowName));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WorkFlow> update(@PathVariable Long id, @RequestBody  WorkFlow wf) {
        return ResponseEntity.ok(workFlowService.updateWorkFlow(id, wf));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        workFlowService.deleteWorkFlow(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/{id}/transaction/{from}")
    public ResponseEntity<List<WorkFlowTransaction>> allowed(@PathVariable Long id, @PathVariable String fromStatus) {
        return ResponseEntity.ok(workFlowService.allowedTransactions(id, fromStatus));
    }
}