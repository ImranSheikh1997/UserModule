package com.usermodule.service;


import com.usermodule.model.entity.Country;
import com.usermodule.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;



    public Iterable<Country> findAllCountry() {
        return countryRepository.findAll();
    }


    public Optional<Country> findCountryById(Long countryId){
        return countryRepository.findById(countryId);
    }
}


