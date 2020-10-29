package com.ftn.sep_udd.service.ESService.impl;

import com.ftn.sep_udd.model.User;
import com.ftn.sep_udd.repository.UserRepository;
import com.ftn.sep_udd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }
}
