package com.zagvladimir.dao;

import com.zagvladimir.model.City;

import java.util.List;
import java.util.Optional;

public interface CityDAO {
    City findCityByName(String name);

    Optional<City> findById(Integer cityId);

    List<City> getAllCityOrderByName();

    List<City> getCitiesWithTails();

    City create(City city);

    City update(City city);

    void delete(Integer cityId);
}
