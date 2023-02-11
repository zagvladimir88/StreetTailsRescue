package com.zagvladimir.service.tail;

import com.zagvladimir.dao.TailDAO;
import com.zagvladimir.model.Tail;
import com.zagvladimir.model.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TailServiceImpl implements TailService{

    private final TailDAO tailDAO;

    @Override
    public List<Tail> findAll() {
        return tailDAO.findAll();
    }

    @Override
    public Tail findById(Integer tailID) {
        return tailDAO.findById(tailID)
                .orElseThrow(() -> new EntityNotFoundException("Tail with id: " + tailID + " not found"));
    }

    @Override
    public List<Tail> findAllWithStatusActive() {
        return tailDAO.findAll()
                .stream().
                filter(tail -> tail.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public Integer create(Tail tail) {
        Tail newTail =  tailDAO.create(tail);
        return newTail.getId();
    }

    @Override
    public void softDeleteTailById(Integer tailId) {
        var tailForDelete = tailDAO.findById(tailId);
        if(tailForDelete.isPresent()){
            tailForDelete.get().setStatus(Status.DELETED);
        }
        tailDAO.update(tailForDelete.get());
    }
}
