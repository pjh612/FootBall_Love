package com.deu.football_love.dto.match;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefuseMatchApplicationRequest {
  Long applicationId;
  String refuseMessage;

  public RefuseMatchApplicationRequest(Long applicationId, String refuseMessage) {
    this.applicationId = applicationId;
    this.refuseMessage = refuseMessage;
  }
}
