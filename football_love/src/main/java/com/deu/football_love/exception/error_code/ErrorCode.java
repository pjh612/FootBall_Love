package com.deu.football_love.exception.error_code;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum ErrorCode implements EnumModel {

  // COMMON
  NOT_TEAM_MEMBER(403, "C001", "Not team member"),
  DUPLICATED(409, "C002", "Already exist"),
  NOT_EXIST_DATA(400,"C003", "Not exist data"),
  NOT_POST_AUTHOR(403,"C004", "Not post author"),
  NOT_ADMIN(403,"C005", "Not admin"),
  NOT_STADIUM_OWNER(403, "C006", "Not stadium owner"),

  // MATCH
  NOT_WAITING_MATCH(400,"M001", "Not waiting match");
  private int status;
  private String code;
  private String message;
  private String detail;

  ErrorCode(int status, String code, String message) {
    this.status = status;
    this.message = message;
    this.code = code;
  }

  @Override
  public String getKey() {
    return this.code;
  }

  @Override
  public String getValue() {
    return this.message;
  }
}
