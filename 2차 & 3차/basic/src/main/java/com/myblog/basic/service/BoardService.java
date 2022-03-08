package com.myblog.basic.service;

import com.myblog.basic.domain.Board;
import com.myblog.basic.domain.Posting;
import com.myblog.basic.repository.BoardRepository;
import com.myblog.basic.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostingRepository postingRepository;

    @Transactional(readOnly = true)
    public Board read(Long id){
        Board board = boardRepository.read(id);
        return board;
    }

    public Long save(String title){
        Board board = new Board();
        board.setTitle(title);
        boardRepository.save(board);
        return board.getId();
    }

    public void update(Long board_id, String title){
        boardRepository.update(board_id, title);
    }

    public void delete(Long board_id){
        boardRepository.delete(board_id);
    }
}
