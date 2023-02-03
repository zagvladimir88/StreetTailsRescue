package com.zagvladimir.dao;

import com.zagvladimir.model.Role;
import com.zagvladimir.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleDAOImpl implements RoleDAO {

    RoleRepository repository;

    @Override
    public List<Role> findRolesByUserId(int roleId) {
        return repository.findRolesByUserid(roleId);
    }

    @Override
    public Optional<Role> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public void create(Role role) {
        repository.save(role);
    }

    @Override
    public void update(Role role) {
        repository.save(role);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
