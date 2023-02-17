package com.zagvladimir.service.tail;

import com.zagvladimir.model.Tail;
import com.zagvladimir.model.enums.Status;

import java.util.List;

public interface TailService {

    List<Tail> findAll();

    Tail findById(Integer tailID);

    List<Tail> findAllWithStatusActive();

    List<Tail> findTailsByCityAndStatusContaining(String city, Status status);

    Integer create(Tail tail);

    void softDeleteTailById(Integer tailId);

    void deleteById(Integer tailId);
}
