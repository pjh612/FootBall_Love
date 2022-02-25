package com.deu.football_love.controller;

import com.deu.football_love.exception.LikeDuplicatedException;
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
            LikeDuplicatedException.class,
    })
    public ResponseEntity<Object> likeDuplicatedException(final RuntimeException ex) {
        log.warn("error", ex);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            NotTeamMemberException.class,
    })
    public ResponseEntity<Object> notTeamMemberException(final RuntimeException ex) {
        log.warn("forbidden", ex);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


}