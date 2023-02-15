package com.zagvladimir.service.city;

import com.zagvladimir.dao.CityDAO;
import com.zagvladimir.model.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService{
    private final CityDAO cityDAO;

    @Override
    public City findCityByName(String name) {
        return cityDAO.findCityByName(name);
    }

    @Override
    public List<City> getAllCityOrderByName() {
        return cityDAO.getAllCityOrderByName();
    }

    @Override
    public List<City> getCitiesWithTails() {
        return cityDAO.getCitiesWithTails();
    }
}
