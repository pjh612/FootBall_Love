package com.deu.football_love.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class LoginResponse {
   private String result;
   private String message;
   private String accessToken;
   private String refreshToken;
   private Long memberNumber;

   public LoginResponse(String result, String message, String accessToken, String refreshToken, Long memberNumber) {
      this.result = result;
      this.message = message;
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
      this.memberNumber = memberNumber;
   }
}
