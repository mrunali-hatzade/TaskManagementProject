package com.taskmanagement.taskmanagementproject.Repository;

import com.taskmanagement.taskmanagementproject.Entity.WorkFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface WorkFlowRepository extends JpaRepository<WorkFlow,Long> {

    Optional<WorkFlow> findByTransactionName(String transationName);
    Optional<WorkFlow> findByWorkFlowName(String workFlowName);

}
