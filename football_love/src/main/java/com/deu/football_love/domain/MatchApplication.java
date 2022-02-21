package com.deu.football_love.domain;

import com.deu.football_love.domain.type.MatchApplicationState;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class MatchApplication extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "match_application_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "matches_id")
	private Matches match;

	@Column(name = "applicaiton_state")
	private MatchApplicationState state;

	@Column(name = "application_refuse_message")
	private String refuseMessage;

	public void refuse(String refuseMessage)
	{
		this.state = MatchApplicationState.REFUSED;
		this.refuseMessage = refuseMessage;
	}
}
