package com.taskmanagement.taskmanagementproject.Service;

import com.taskmanagement.taskmanagementproject.Entity.WorkFlow;
import com.taskmanagement.taskmanagementproject.Entity.WorkFlowTransaction;
import com.taskmanagement.taskmanagementproject.Repository.WorkFlowRepository;
import com.taskmanagement.taskmanagementproject.Repository.WorkFlowTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkFlowService {

    private final WorkFlowRepository workFlowRepo;
    private final WorkFlowTransactionRepository workFlowTransactionRepo;

    // CREATE
    @Transactional
    public WorkFlow createWorkFlow(WorkFlow wf) {
        for (WorkFlowTransaction trans : wf.getTransaction()) {
            trans.setWorkflow(wf);
        }
        return workFlowRepo.save(wf);
    }

    // READ ALL
    public List<WorkFlow> listAll() {
        return workFlowRepo.findAll();
    }

    // READ BY ID
    public WorkFlow getById(Long id) {
        return workFlowRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Workflow not found"));
    }

    // READ BY NAME
    public Optional<WorkFlow> findByWorkFlowName(String name) {
        return workFlowRepo.findByWorkFlowName(name);
    }

    // UPDATE
    @Transactional
    public WorkFlow updateWorkFlow(Long id, WorkFlow updated) {

        WorkFlow wf = getById(id);

        wf.setWorkFlowName(updated.getWorkFlowName());
        wf.setWorkFlowDescription(updated.getWorkFlowDescription());

        wf.getTransaction().clear();

        if (updated.getTransaction() != null) {
            for (WorkFlowTransaction trans : updated.getTransaction()) {
                trans.setWorkflow(wf);
                wf.getTransaction().add(trans);
            }
        }

        return workFlowRepo.save(wf);
    }

    // DELETE
    public void deleteWorkFlow(Long id) {
        workFlowRepo.deleteById(id);
    }

    // ALLOWED TRANSACTIONS
    public List<WorkFlowTransaction> allowedTransactions(Long workflowId, String fromStatus) {
        return workFlowTransactionRepo.findByWorkIdAndFromStatus(workflowId, fromStatus);
    }

    // ROLE VALIDATION (IMPORTANT METHOD)
    public boolean isTransactionAllowed(Long workflowId,
                                        String fromStatus,
                                        String toStatus,
                                        Set<String> userRoles) {

        List<WorkFlowTransaction> transactions =
                workFlowTransactionRepo.findByWorkIdAndFromStatus(workflowId, fromStatus);

        for (WorkFlowTransaction trans : transactions) {

            if (trans.getToStatus().equals(toStatus)) {

                String allowed = trans.getAllowedRole();

                // No role restriction
                if (allowed == null || allowed.isEmpty()) {
                    return true;
                }

                Set<String> allowedSet = Arrays.stream(allowed.split(","))
                        .map(String::trim)
                        .collect(Collectors.toSet());

                for (String role : userRoles) {
                    if (allowedSet.contains(role)) {
                        return true;
                    }
                }
                return false; // role not allowed
            }
        }
        return false; // transition not found
    }
}
