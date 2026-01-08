package com.taskmanagement.taskmanagementproject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="board_cards",indexes= {@Index(columnList = "board_id,column_id, position ")})

public class BoardCard {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    private Long id;
    private Long boardId;
    private Long issueid;
    private Integer position;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="column_id")
    private BoardColumn column;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getIssueid() {
        return issueid;
    }

    public void setIssueid(Long issueid) {
        this.issueid = issueid;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public BoardColumn getColumn() {
        return column;
    }

    public void setColumn(BoardColumn column) {
        this.column = column;
    }
}
