package com.usermodule.repository;

import com.usermodule.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City, Long> {
    List<City> findCitiesByState_Id(Long stateId);
}


