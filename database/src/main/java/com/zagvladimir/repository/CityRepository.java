package com.zagvladimir.repository;

import com.zagvladimir.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Integer> {

    City findCityByName(String name);

    @Query("SELECT c FROM City c ORDER BY c.name")
    List<City> getAllCityOrderByName();
}
