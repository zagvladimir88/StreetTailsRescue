package com.zagvladimir.service.tail;

import com.zagvladimir.model.Tail;

import java.util.List;

public interface TailService {
    List<Tail> findAll();

    Integer create(Tail tail);
}
