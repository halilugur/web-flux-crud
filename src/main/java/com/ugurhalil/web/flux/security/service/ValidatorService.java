package com.ugurhalil.web.flux.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ServerWebInputException;

@Service
@RequiredArgsConstructor
public class ValidatorService {
    private final Validator validator;

    public <T> void validate(T request) {
        Errors errors = new BeanPropertyBindingResult(request, request.getClass().getSimpleName());
        validator.validate(request, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
