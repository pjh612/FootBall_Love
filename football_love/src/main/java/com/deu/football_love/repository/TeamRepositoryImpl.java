package com.deu.football_love.repository;

import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamAdmin;
import com.deu.football_love.domain.TeamMember;
import com.deu.football_love.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {

    private final EntityManager em;

    public void insertTeam(TeamAdmin teamAdmin, Team team)
    {
        em.persist(teamAdmin);
        em.persist(team);
    }
    public Team selectTeam(Long id)
    {
        return em.find(Team.class, id);
    }

    public Team selectTeamByName(String teamName)
    {
        List<Team> team = em.createQuery("SELECT t FROM Team t WHERE t.name = :teamName", Team.class).setParameter("teamName", teamName).getResultList();
        if(team.size() == 1)
            return team.get(0);
        else
            return null;
    }

    @Override
    public void insertNewTeamMember(TeamMember newTeamMember) {
        em.persist(newTeamMember);
    }
}
