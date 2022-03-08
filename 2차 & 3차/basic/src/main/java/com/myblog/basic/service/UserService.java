package com.myblog.basic.service;

import com.myblog.basic.domain.User;
import com.myblog.basic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findOne(Long u_id){
        return userRepository.findOne(u_id);
    }

    public void save(String name, Long age, String email){
        userRepository.save(name, age, email);
    }

    public void put(Long user_id, String name, Long age, String email){
        userRepository.put(user_id, name, age, email);
    }

    public void delete(Long user_id){
        userRepository.delete(user_id);
    }

}
