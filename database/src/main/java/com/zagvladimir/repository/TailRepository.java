package com.zagvladimir.repository;

import com.zagvladimir.model.Tail;
import com.zagvladimir.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TailRepository extends JpaRepository<Tail,Integer> {

    List<Tail> findTailsByCityAndStatusContaining(String city, Status status);
}
