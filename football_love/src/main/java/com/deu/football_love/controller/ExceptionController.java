package com.deu.football_love.controller;

import com.deu.football_love.exception.LikeDuplicatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({
            LikeDuplicatedException.class,
    })
    public ResponseEntity<Object> LikeDuplicatedException(final RuntimeException ex) {
        log.warn("error", ex);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }


}