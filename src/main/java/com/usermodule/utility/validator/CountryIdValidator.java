package com.usermodule.utility.validator;

import com.usermodule.model.entity.Country;
import com.usermodule.service.CountryService;
import com.usermodule.utility.validator.annotations.CountryID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Slf4j
public class CountryIdValidator implements ConstraintValidator<CountryID, Long> {

    @Autowired
    private CountryService countryService;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        final Optional<Country> country = countryService.findCountryById(value);
        log.info("Country for {} found {}", value, country.isPresent());
        return false;
    }
}
