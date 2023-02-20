package com.zagvladimir.service.city;

import com.zagvladimir.dao.CityDAOImpl;
import com.zagvladimir.model.City;
import com.zagvladimir.model.Tail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

    @Mock
    private CityDAOImpl cityDAO;

    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    void findCityByName() {
        City expectedCity = new City();
        expectedCity.setName("Zhlobin");

        when(cityDAO.findCityByName("Zhlobin")).thenReturn(expectedCity);

        City actualCity = cityService.findCityByName("Zhlobin");
        assertEquals(expectedCity,actualCity);
    }

    @Test
    void getAllCityOrderByName() {
        List<City> expectedListCity = new ArrayList<>();
        City zhlobin = new City();
        zhlobin.setName("Zhlobin");
        City baranovichi = new City();
        baranovichi.setName("Baranovichi");
        expectedListCity.add(zhlobin);
        expectedListCity.add(baranovichi);



        when(cityDAO.getAllCityOrderByName()).thenReturn(expectedListCity);

        List<City> actualListCity = cityService.getAllCityOrderByName();
        assertEquals(expectedListCity,actualListCity);

    }

    @Test
    void getCitiesWithTails() {
        List<City> expectedListCity = new ArrayList<>();

        City zhlobin = new City();
        zhlobin.setName("Zhlobin");
        Tail tail = new Tail();
        tail.setCity(zhlobin);
        HashSet<Tail> tailsList = new HashSet<>();
        tailsList.add(tail);
        zhlobin.setTails(tailsList);
        expectedListCity.add(zhlobin);



        when(cityDAO.getCitiesWithTails()).thenReturn(expectedListCity);

        List<City> actualListCity = cityService.getCitiesWithTails();
        assertEquals(expectedListCity,actualListCity);
        assertThat(actualListCity).isNotEmpty();

    }
}