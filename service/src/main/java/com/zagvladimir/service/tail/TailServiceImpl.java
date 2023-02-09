package com.zagvladimir.service.tail;

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
    public Integer create(Tail tail) {
        Tail newTail =  tailDAO.create(tail);
        return newTail.getId();
    }
}
