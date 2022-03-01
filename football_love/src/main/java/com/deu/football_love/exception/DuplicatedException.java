package com.deu.football_love.exception;

public class DuplicatedException extends RuntimeException {

  public DuplicatedException() {
  }

  public DuplicatedException(String message) {
    super(message);
  }
}
