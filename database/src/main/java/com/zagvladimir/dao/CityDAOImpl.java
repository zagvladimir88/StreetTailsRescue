package com.zagvladimir.dao;

import com.zagvladimir.model.City;
import com.zagvladimir.repository.CityRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CityDAOImpl implements CityDAO{
    CityRepository repository;

    @Override
    public City findCityByName(String name) {
        return repository.findCityByName(name);
    }

    @Override
    public Optional<City> findById(Integer cityId) {
        return repository.findById(cityId);
    }

    @Override
    public List<City> getAllCityOrderByName() {
        return repository.getAllCityOrderByName();
    }

    @Override
    public List<City> getCitiesWithTails() {
        return repository.getCitiesWithTails();
    }

    @Override
    public City create(City city) {
        return repository.save(city);
    }

    @Override
    public City update(City city) {
        return repository.save(city);
    }

    @Override
    public void delete(Integer cityId) {
        repository.deleteById(cityId);
    }


}
