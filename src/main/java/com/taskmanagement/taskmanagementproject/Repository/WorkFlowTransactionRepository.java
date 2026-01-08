package com.taskmanagement.taskmanagementproject.Repository;

import com.taskmanagement.taskmanagementproject.Entity.WorkFlowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface WorkFlowTransactionRepository extends JpaRepository<WorkFlowTransaction, Long> {
    List<WorkFlowTransaction> findByWorkFlowId(String workflowID);
    List<WorkFlowTransaction> findByWorkIdAndFromStatus(Long workflowId, String fromStatus);

}
