package com.billa.springdatajpa.repository;

import com.billa.springdatajpa.domaine.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByName(String userName);
    User findUserByEmail(String email);
}
