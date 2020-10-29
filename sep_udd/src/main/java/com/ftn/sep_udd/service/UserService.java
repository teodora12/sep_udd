package com.ftn.sep_udd.service;

import com.ftn.sep_udd.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User findUserByEmail(String email);
    User findUserByUsername(String username);

}
