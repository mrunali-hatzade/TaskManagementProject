package com.taskmanagement.taskmanagementproject.Repository;

import com.taskmanagement.taskmanagementproject.Entity.BoardCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardCardRepository extends JpaRepository<BoardCard, Long> {
    List<BoardCard> findByboardIdAndColumnIdOrderByPosition(Long boardId,  Long columnId);
    long countByboardIdAndColumnId(Long boardId,  Long columnId);

    Optional<BoardCard> findByIssueId(Long issueId);
}
