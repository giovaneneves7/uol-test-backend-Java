package com.github.giovaneneves7.api.controller.v1.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;

public class ResultError {

    public static HashMap<String, String> getResultErrors(BindingResult result){

        HashMap<String, String> erros = new HashMap<>();

        for(FieldError erro : result.getFieldErrors())
            erros.put(erro.getField(), erro.getDefaultMessage());

        return erros;

    }

}
