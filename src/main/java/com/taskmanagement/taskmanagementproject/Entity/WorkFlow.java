package com.taskmanagement.taskmanagementproject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.config.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "work_flows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class WorkFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String workFlowName;

    @Column(nullable = false, unique = true)
    private String workFlowDescription;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkFlowTransaction> transaction = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkFlowName() {
        return workFlowName;
    }

    public void setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
    }

    public String getWorkFlowDescription() {
        return workFlowDescription;
    }

    public void setWorkFlowDiscription(String workFlowDescription) {
        this.workFlowDescription = workFlowDescription;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<WorkFlowTransaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<WorkFlowTransaction> transaction) {
        this.transaction = transaction;
    }

}

