package service;

import model.City;
import repository.CityRepository;

import java.util.List;

public class CityService {

    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities() {
        return cityRepository.getAll();
    }

    public City findById(int id) {
        return cityRepository.findById(id);
    }
}