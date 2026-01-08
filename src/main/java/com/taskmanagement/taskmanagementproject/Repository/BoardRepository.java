package com.taskmanagement.taskmanagementproject.Repository;

import com.taskmanagement.taskmanagementproject.Entity.Board;
import com.taskmanagement.taskmanagementproject.Entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board>findByProjectKey(String projectKey);



}
