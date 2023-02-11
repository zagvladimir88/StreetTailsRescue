package com.zagvladimir.service.tail;

import com.zagvladimir.model.Tail;

import java.util.List;

public interface TailService {
    List<Tail> findAll();
    Tail findById(Integer tailID);
    List<Tail> findAllWithStatusActive();

    Integer create(Tail tail);

    void softDeleteTailById(Integer tailId);
}
