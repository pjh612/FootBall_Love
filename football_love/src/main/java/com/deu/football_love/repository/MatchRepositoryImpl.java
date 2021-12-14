package com.deu.football_love.repository;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.deu.football_love.domain.MatchApplication;
import com.deu.football_love.domain.Matches;
import com.deu.football_love.domain.Stadium;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MatchRepositoryImpl implements MatchRepository {

	private final EntityManager em;

	@Override
	public Matches selectMatch(Long id) {
		return em.find(Matches.class, id);
	}

	@Override
	public void insertMatch(Matches match) {
		em.persist(match);
	}

	@Override
	public Matches updateMatch(Long matchId) {
		Matches matches = em.find(Matches.class, matchId);
		matches.setApproval(true);
		return matches;
	}

	@Override
	public MatchApplication updateMatchApplication(Long matchApplicationId) {
		MatchApplication matchApplication = em.find(MatchApplication.class, matchApplicationId);
		matchApplication.setApproval(true);
		return matchApplication;
	}

	@Override
	public void insertMatchApplication(MatchApplication matchApplication) {
		em.persist(matchApplication);
	}

	@Override
	public void deleteMatch(Matches match) {
		em.remove(match);
	}

	@Override
	public Matches updateMatch(Long matchId, Stadium stadium, LocalDateTime reservationTime) {
		Matches match = em.find(Matches.class, matchId);
		match.setStadium(stadium);
		match.setReservationTime(reservationTime);
		return match;
	}

}
