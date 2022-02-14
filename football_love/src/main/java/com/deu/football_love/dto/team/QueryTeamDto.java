package com.deu.football_love.dto.team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.deu.football_love.domain.Team;
import com.deu.football_love.dto.BaseDto;
import com.deu.football_love.dto.board.BoardDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryTeamDto extends BaseDto {

  private Long id;
  private String name;
  private String profileImgUri;
  private String introduce;
  private List<String> teamMembers = new ArrayList<>();
  private List<BoardDto> boards = new ArrayList<>();

  private QueryTeamDto(Team team) {
    this.id = team.getId();
    this.name = team.getName();
    this.profileImgUri = team.getProfileImgUri();
    this.introduce = team.getIntroduce();
    team.getTeamMembers().forEach(teamMember -> {
      this.teamMembers.add(teamMember.getMember().getId());
    });
    boards =
        team.getBoards().stream().map(board -> new BoardDto(board)).collect(Collectors.toList());
  }

  public static QueryTeamDto from(Team team) {
    return new QueryTeamDto(team);
  }


}
