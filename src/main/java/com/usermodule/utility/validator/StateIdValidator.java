package com.usermodule.utility.validator;

import com.usermodule.model.entity.State;
import com.usermodule.service.StateService;
import com.usermodule.utility.validator.annotations.StateId;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Slf4j
public class StateIdValidator implements ConstraintValidator<StateId, Long> {

    private final StateService stateService;

    public StateIdValidator(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        final Optional<State> stateOptional= stateService.findStateById(value);
        log.info("State for {} found {} ",value,stateOptional.isPresent());
        return stateOptional.isPresent();
    }
}
