
package com.usermodule.service;


import com.usermodule.model.entity.City;
import com.usermodule.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private  CityRepository cityRepository;

        public Iterable<City> getCitiesByState(Long stateId) {
            return cityRepository.findCitiesByState_Id(stateId);
        }
        }
