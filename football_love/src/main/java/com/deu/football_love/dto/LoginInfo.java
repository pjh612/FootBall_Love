package com.deu.football_love.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString

public class LoginInfo {
   private String result;
   private String message;
   private String accessToken;
   private String refreshToken;
}
