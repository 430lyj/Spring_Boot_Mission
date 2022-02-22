package com.myblog.basic.repository;

import com.myblog.basic.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public void save(Board board){
        em.persist(board);
    }

    public Board read(Long id){
        return em.find(Board.class, id);
    }

    public Long update(Long id, String title){
        Board board = em.find(Board.class, id);
        board.setTitle(title);
        em.merge(board);
        em.flush();
        return board.getId();
    }

    public void delete(Long id){
        Board board = em.find(Board.class, id);
        em.remove(board);
    }
}
