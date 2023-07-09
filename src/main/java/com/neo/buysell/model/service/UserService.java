package com.neo.buysell.model.service;

import com.neo.buysell.model.entity.Ad;
import com.neo.buysell.model.entity.User;
import com.neo.buysell.model.exception.particular.EntityNotFound;
import com.neo.buysell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFound(User.class, HttpStatus.NOT_FOUND));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
