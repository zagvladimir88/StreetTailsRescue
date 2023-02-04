package com.zagvladimir.dao;

import com.zagvladimir.model.Tail;

import java.util.List;
import java.util.Optional;

public interface TailDAO {
    Optional<Tail> findById(int id);
    List<Tail> findAll();
    Tail create(Tail tail);
    Tail update(Tail tail);
    void delete(int id);
}
