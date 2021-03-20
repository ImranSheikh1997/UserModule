package com.usermodule.repository;


import com.usermodule.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CountryRepository extends
        JpaRepository<Country, Long>
{
}


