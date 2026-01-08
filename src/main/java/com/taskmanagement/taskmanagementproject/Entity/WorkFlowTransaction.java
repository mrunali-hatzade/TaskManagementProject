package com.taskmanagement.taskmanagementproject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workflow_transactions", indexes = {@Index(name="idx_wf_from_to", columnList = "workflow_id, fromStatus,toStatus")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class WorkFlowTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name="workflow_id", nullable = false)
    private WorkFlow workflow;

    private String fromStatus;
    private String toStatus;

    private String transactionlName;
    private String allowedRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkFlow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkFlow workflow) {
        this.workflow = workflow;
    }

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    public String getTransactionlName() {
        return transactionlName;
    }

    public void setTransactionlName(String transactionlName) {
        this.transactionlName = transactionlName;
    }

    public String getAllowedRole() {
        return allowedRole;
    }

    public void setAllowedRole(String allowedRole) {
        this.allowedRole = allowedRole;
    }
}
