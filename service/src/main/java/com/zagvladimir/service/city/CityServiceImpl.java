package com.zagvladimir.service.city;

import com.zagvladimir.model.City;
import com.zagvladimir.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService{
    private final CityRepository cityRepository;

    @Override
    public City findCityByName(String name) {
        return cityRepository.findCityByName(name);
    }

    @Override
    public List<City> getAllCityOrderByName() {
        return cityRepository.getAllCityOrderByName();
    }

    @Override
    public List<City> getCitiesWithTails() {
        return cityRepository.getCitiesWithTails();
    }
}
