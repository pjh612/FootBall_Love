package com.deu.football_love.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class TokenInfo {
   private String result;
   private String message;
   private String accessToken;
   private String refreshToken;

   public TokenInfo(String result, String message, String accessToken, String refreshToken) {
      this.result = result;
      this.message = message;
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
   }
}
