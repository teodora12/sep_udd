package com.ftn.sep_udd.repository;

import com.ftn.sep_udd.model.Authority;
import com.ftn.sep_udd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    User findUserByAuthorities(Authority authority);

}
