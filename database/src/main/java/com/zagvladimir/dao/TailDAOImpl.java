package com.zagvladimir.dao;

import com.zagvladimir.model.Tail;
import com.zagvladimir.repository.TailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TailDAOImpl implements TailDAO {

    TailRepository tailRepository;

    @Override
    public Optional<Tail> findById(int id) {
        return tailRepository.findById(id);
    }

    @Override
    public List<Tail> findAll() {
        return tailRepository.findAll();
    }

    @Override
    public Tail create(Tail tail) {
        return tailRepository.save(tail);
    }

    @Override
    public Tail update(Tail tail) {
        return tailRepository.save(tail);
    }

    @Override
    public void delete(int id) {
        tailRepository.deleteById(id);
    }
}
