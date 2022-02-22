package com.myblog.basic.repository;

import com.myblog.basic.domain.Board;
import com.myblog.basic.domain.Posting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostingRepository {
    private final EntityManager em;

    public void save(Posting posting){
        em.persist(posting);
    }

    public Posting read(Long id){
        return em.find(Posting.class, id);
    }

    public List<Posting> findByBoard (Long board_id){
        return em.createQuery("select p from Posting  p" +
                " where p.board.id = :board_id ", Posting.class)
                .setParameter("board_id", board_id)
                .getResultList();
    }

    public Long update(Long id, String title, String body, Board board){
        em.getTransaction().begin();
        Posting posting = em.find(Posting.class, id);
        posting.setTitle(title);
        posting.setBody(body);
        posting.setBoard(board);
        em.merge(board);
        em.flush();
        return board.getId();
    }

    public void delete(Posting posting){
        em.remove(posting);
    }
}
