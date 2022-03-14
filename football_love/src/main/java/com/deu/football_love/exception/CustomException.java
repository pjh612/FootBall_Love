package com.deu.football_love.exception;

import com.deu.football_love.exception.error_code.ErrorCode;

public class CustomException extends RuntimeException {

  private ErrorCode errorCode;

  public CustomException(String message, ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return this.errorCode;
  }


}

