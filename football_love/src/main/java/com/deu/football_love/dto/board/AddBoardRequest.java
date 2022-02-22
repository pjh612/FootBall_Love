package com.deu.football_love.dto.board;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.deu.football_love.domain.type.BoardType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddBoardRequest {
  @NotNull
  @Size(min = 1, max = 30)
  private String boardName;

  @NotNull
  private BoardType boardType;
}
