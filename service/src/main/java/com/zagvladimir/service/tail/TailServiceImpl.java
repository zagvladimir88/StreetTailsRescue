package com.zagvladimir.service.tail;

import com.zagvladimir.model.Tail;
import com.zagvladimir.model.enums.Status;
import com.zagvladimir.repository.TailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TailServiceImpl implements TailService{

    private final TailRepository tailRepository;

    @Override
    public List<Tail> findAll() {
        return tailRepository.findAll();
    }

    @Override
    public Tail findById(Integer tailID) {
        return tailRepository.findById(tailID)
                .orElseThrow(() -> new EntityNotFoundException("Tail with id: " + tailID + " not found"));
    }

    @Override
    public List<Tail> findAllWithStatusActive() {
        return tailRepository.findAll()
                .stream().
                filter(tail -> tail.getStatus() == Status.ACTIVE).toList();
    }

    @Override
    public Integer create(Tail tail) {
        Tail newTail =  tailRepository.save(tail);
        return newTail.getId();
    }

    @Override
    public void softDeleteTailById(Integer tailId) {
        var tailForDelete = tailRepository.findById(tailId);
        if(tailForDelete.isPresent()){
            tailForDelete.get().setStatus(Status.DELETED);
            tailRepository.save(tailForDelete.get());
        }
    }

    @Override
    public void deleteById(Integer tailId) {
        tailRepository.deleteById(tailId);
    }
}
