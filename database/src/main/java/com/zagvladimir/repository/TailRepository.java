package com.zagvladimir.repository;

import com.zagvladimir.model.Tail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TailRepository extends JpaRepository<Tail,Integer> {

}
