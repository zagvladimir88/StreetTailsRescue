package com.zagvladimir.service.city;

import com.zagvladimir.model.City;

import java.util.List;

public interface CityService {
    City findCityByName(String name);

    List<City> getAllCityOrderByName();
}
