package com.zagvladimir.service;

import com.zagvladimir.dao.TailDAO;
import com.zagvladimir.model.Tail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TailServiceImpl implements TailService{

    private final TailDAO tailDAO;

    @Override
    public List<Tail> findAll() {
        return tailDAO.findAll();
    }

    @Override
    public void create(Tail tail) {
        tailDAO.create(tail);
    }
}
