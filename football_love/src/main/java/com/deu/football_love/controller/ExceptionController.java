package com.deu.football_love.controller;

import com.deu.football_love.exception.DuplicatedException;
import com.deu.football_love.exception.NotTeamMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({
            NotTeamMemberException.class,
    })
    public ResponseEntity<Object> notTeamMemberException(final RuntimeException ex) {
        log.warn("forbidden", ex);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({
        DuplicatedException.class,
    })
    public ResponseEntity<Object> duplicatedException(final RuntimeException ex) {
        log.warn("conflict", ex);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}