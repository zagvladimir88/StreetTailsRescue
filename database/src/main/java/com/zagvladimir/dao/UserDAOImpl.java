package com.zagvladimir.dao;

import com.zagvladimir.model.User;
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
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
       return userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);

    }

    @Override
    public Optional<User> findUserByActivationCode(String code) {
        return userRepository.findUserByActivationCode(code);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }
}
