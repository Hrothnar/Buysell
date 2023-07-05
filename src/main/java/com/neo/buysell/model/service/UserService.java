package com.neo.buysell.model.service;

import com.neo.buysell.model.entity.User;
import com.neo.buysell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException());//TODO
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
