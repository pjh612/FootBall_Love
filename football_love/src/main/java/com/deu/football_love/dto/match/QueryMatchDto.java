package com.deu.football_love.dto.match;

import com.deu.football_love.domain.type.MatchState;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QueryMatchDto {
	private Long matchId;
	private Long teamAId;
	private String teamAName;
	private Long teamBId;
	private String teamBName;
	private Long stadiumId;
	private LocalDateTime reservationTime;
	private MatchState state;
	private String refuseMessage;

	public QueryMatchDto(Long matchId, Long teamAId, String teamAName, Long teamBId,
			String teamBName, Long stadiumId, LocalDateTime reservationTime,
			MatchState state, String refuseMessage) {
		this.matchId = matchId;
		this.teamAId = teamAId;
		this.teamAName = teamAName;
		this.teamBId = teamBId;
		this.teamBName = teamBName;
		this.stadiumId = stadiumId;
		this.reservationTime = reservationTime;
		this.state = state;
		this.refuseMessage = refuseMessage;
	}

	public QueryMatchDto(Long matchId, Long teamAId, String teamAName, Long stadiumId,
			LocalDateTime reservationTime, MatchState state, String refuseMessage) {
		this.matchId = matchId;
		this.teamAId = teamAId;
		this.teamAName = teamAName;
		this.stadiumId = stadiumId;
		this.reservationTime = reservationTime;
		this.state = state;
		this.refuseMessage = refuseMessage;
	}

	public QueryMatchDto(Long matchId, Long stadiumId, LocalDateTime reservationTime,
			MatchState state, String refuseMessage) {
		this.matchId = matchId;
		this.stadiumId = stadiumId;
		this.reservationTime = reservationTime;
		this.state = state;
		this.refuseMessage = refuseMessage;
	}
}
