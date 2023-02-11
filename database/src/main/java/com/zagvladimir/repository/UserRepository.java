package com.zagvladimir.repository;

import com.zagvladimir.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findUserByActivationCode(String code);

    Optional<User> findUserByLogin(String login);
}
