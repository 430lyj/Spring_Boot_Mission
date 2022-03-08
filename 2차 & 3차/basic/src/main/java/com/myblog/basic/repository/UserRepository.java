package com.myblog.basic.repository;

import com.myblog.basic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public User findOne(Long u_id){
        return em.find(User.class, u_id);
    }

    public void save(String name, Long age, String email){
        User user = new User();
        user.setEmail(email);
        user.setAge(age);
        user.setName(name);
        em.persist(user);
    }

    public void put(Long user_id, String name, Long age, String email){
        User user = em.find(User.class, user_id);
        user.setName(name);
        user.setAge(age);
        user.setEmail(email);
        em.merge(user);
        em.flush();
    }

    public void delete (Long user_id){
        em.remove(findOne(user_id));
    }
}
