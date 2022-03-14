package com.deu.football_love.controller;

import com.deu.football_love.exception.CustomException;
import com.deu.football_love.exception.DuplicatedException;
import com.deu.football_love.exception.NotExistDataException;
import com.deu.football_love.exception.NotTeamMemberException;
import com.deu.football_love.exception.error_code.ErrorCode;
import com.deu.football_love.exception.error_code.ErrorResponse;
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
  public ResponseEntity<ErrorResponse> notTeamMemberException(final RuntimeException ex) {
    log.warn("forbidden", ex);
    ErrorResponse response = new ErrorResponse(ErrorCode.NOT_TEAM_MEMBER);
    response.setDetail(ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler({
      DuplicatedException.class,
  })
  public ResponseEntity<Object> duplicatedException(final RuntimeException ex) {
    log.warn("conflict", ex);
    return new ResponseEntity<>(HttpStatus.CONFLICT);
  }

  @ExceptionHandler({
      NotExistDataException.class,
  })
  public ResponseEntity<Object> noExistDataException(final RuntimeException ex) {
    ErrorResponse response = new ErrorResponse(ErrorCode.NOT_EXIST_DATA);
    response.setDetail(ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
  }


  @ExceptionHandler({CustomException.class})
  public ResponseEntity customException(final CustomException ex) {
    ErrorResponse response = new ErrorResponse(ex.getErrorCode());
    response.setDetail(ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity illegalArgumentException(final IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}