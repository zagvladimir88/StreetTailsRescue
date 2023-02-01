package com.zagvladimir.dao;

import com.zagvladimir.entity.User;
import com.zagvladimir.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO{

    private final UserRepository userRepository;

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(int id) {

    }
}
