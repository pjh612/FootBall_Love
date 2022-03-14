package com.deu.football_love.exception;

public class NotExistDataException extends RuntimeException{
  public NotExistDataException() {
  }
  public NotExistDataException(String message) {
    super(message);
  }
}
