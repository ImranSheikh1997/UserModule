package com.usermodule.repository;

import com.usermodule.model.entity.State;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends PagingAndSortingRepository<State, Long> {
    List<State> findStatesByCountry_Id(Long countryId);
}
