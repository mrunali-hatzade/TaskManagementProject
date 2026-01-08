package com.taskmanagement.taskmanagementproject.Service;

import com.taskmanagement.taskmanagementproject.Entity.Board;
import com.taskmanagement.taskmanagementproject.Entity.BoardCard;
import com.taskmanagement.taskmanagementproject.Entity.BoardColumn;
import com.taskmanagement.taskmanagementproject.Entity.Issue;
import com.taskmanagement.taskmanagementproject.Repository.BoardCardRepository;
import com.taskmanagement.taskmanagementproject.Repository.BoardColumnRepository;
import com.taskmanagement.taskmanagementproject.Repository.BoardRepository;
import com.taskmanagement.taskmanagementproject.Repository.IssueRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    private BoardColumnRepository boardColumnRepo;

    @Autowired
    private BoardCardRepository  boardCardRepo;

    @Autowired
    private IssueRepository   issueRepo;

    public Board createBoard(Board board){
        return boardRepo.save(board);
    }

    public Optional<Board> findByBoardId(Long id){
        return boardRepo.findById(id);
    }
    public Optional<BoardColumn>getByColumns(Long boardId){
        return boardColumnRepo.findByboardIdOrderByPosition(boardId);
    }
    public List<BoardCard> getCardsForColumn(Long boardId,Long columnId){
        return boardCardRepo.findByboardIdAndColumnIdOrderByPosition(boardId,columnId);
    }

    @Transactional
    public BoardCard addIssueToBoard(Long boardId,Long columnId,Long issueId){
        Issue issue =issueRepo.findById(issueId).orElseThrow(()->new RuntimeException("Issue Not Found"));
        boardCardRepo.findByIssueId(issueId).ifPresent(boardCardRepo::delete);

        BoardColumn column= boardColumnRepo.findById(columnId).orElseThrow(()->new RuntimeException("Column Not Found"));
        if(column.getWipLimit() != null && column.getWipLimit() > 0){
            long count =boardCardRepo.countByboardIdAndColumnId(boardId,columnId);
            if(count >= column.getWipLimit()) {
                throw new RuntimeException("Wip Limit reached for column :"+column .getName());

            }
        }
        List<BoardCard >existing =boardCardRepo.findByboardIdAndColumnIdOrderByPosition(boardId,columnId);
        int post =existing.size();

        BoardCard card=new BoardCard();

        card.setBoardId(boardId);
        card.setColumn(column);
        card.setIssueid(issueId);
        card.setPosition(post);

        card= boardCardRepo.save(card);
        if(column.getStatusKey() != null){
            issue.setStatus(Enum.valueOf(com.taskmanagement.taskmanagementproject.Enum.IssueStatus.class, column.getStatusKey()));
        }
        return card;
    }
    @Transactional
    public void moveCard(Long boardId, Long tocolumnId, Long cardId, int position, String performBy)
    {
        BoardCard card= boardCardRepo.getById(cardId);
        if (card== null){
            throw new RuntimeException("Card Not Found");
        }

        BoardColumn from= card.getColumn();

        BoardColumn to= boardColumnRepo.findById(tocolumnId).orElseThrow (()-> new RuntimeException("toStatus not found "));
        if (to==null){
            throw new RuntimeException("Target not found");
        }
        if(to.getWipLimit() != null && to.getWipLimit() > 0){
            long count =boardCardRepo.countByboardIdAndColumnId(boardId,tocolumnId);
            if(Objects.equals(from.getId(),to.getId()) && count >= to.getWipLimit()) {
                throw new RuntimeException("Wip limit Exceeded for column : "+ to.getWipLimit());

            }
        }
        List<BoardCard>fromList=boardCardRepo.findByboardIdAndColumnIdOrderByPosition(boardId,from.getId());
        for(BoardCard bc:fromList){
            if (bc.getPosition() >card.getPosition()){
                bc.setPosition(bc.getPosition()-1);
                boardCardRepo.save(bc);

            }
        }
        List<BoardCard>toList=boardCardRepo.findByboardIdAndColumnIdOrderByPosition(boardId,to.getId());
        for(BoardCard bc:toList){
            if(bc.getPosition()>= position){
                bc.setPosition(bc.getPosition()+1);
                boardCardRepo.save(bc);
            }
        }
        card.setColumn(to);
        card.setPosition(position);
        boardCardRepo.save(card);

        Issue issue= issueRepo.findById(card.getIssueid()).orElseThrow(()-> new RuntimeException("Issue Not Found"));
        if (to.getStatusKey() != null){
            issue.setStatus(Enum.valueOf(com.taskmanagement.taskmanagementproject.Enum.IssueStatus.class, to.getStatusKey()));
            issue= issueRepo.save(issue);
        }
    }
    @Transactional
    public void recordColumn(Long boardId,Long columnId,List <Long> orderCardIds){
        int post =0;
        for(Long cid:orderCardIds) {
            BoardCard card = boardCardRepo.findById(cid).orElseThrow(() -> new RuntimeException("Card Not Found"));
            card.setPosition(post++);
            boardCardRepo.save(card);
        }
    }

    @Transactional
    public void startSprint(Long sprintId) {

    }

    @Transactional
    public void completeSprint(Long sprintId) {

    }


}
