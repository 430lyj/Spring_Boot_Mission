package com.myblog.basic.service;

import com.myblog.basic.domain.Board;
import com.myblog.basic.domain.Posting;
import com.myblog.basic.domain.User;
import com.myblog.basic.repository.BoardRepository;
import com.myblog.basic.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostingService {

    private final BoardRepository boardRepository;
    private final PostingRepository postingRepository;

    @Transactional(readOnly = true)
    public Posting read(Long id){
        Posting posting = postingRepository.read(id);
        return posting;
    }

    public Long save(String title, String body, User writer, Long board_id, String password){
        Board board = boardRepository.read(board_id);
        Posting posting = Posting.createPosting(board, title, body, writer, password);
        postingRepository.save(posting);
        return posting.getId();
    }

    public void update(Long posting_id, String title, String body, Long board_id){
        Board board = boardRepository.read(board_id);
        postingRepository.update(posting_id, title, body, board);
    }

    public void delete(Long posting_id){
        Posting posting = postingRepository.read(posting_id);
        postingRepository.delete(posting);
    }

    @Transactional(readOnly = true)
    public List<Posting> findPostings(Long board_id){
        return postingRepository.findByBoard(board_id);
    }
}
