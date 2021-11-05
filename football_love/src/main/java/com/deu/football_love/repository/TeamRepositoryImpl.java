package com.deu.football_love.repository;

import com.deu.football_love.domain.ApplicationJoinTeam;
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

    @Override
    public void insertTeam(TeamAdmin teamAdmin, Team team)
    {
        em.persist(teamAdmin);
        em.persist(team);
    }

    @Override
    public Team selectTeam(String teamName)
    {
        return em.find(Team.class, teamName);
    }

    @Override
    public List<TeamMember> selectTeamMember(String teamName, String memberId)
    {
        if (memberId != null) {
            return em.createQuery("SELECT tm FROM TeamMember tm WHERE tm.team.name =:teamName AND tm.member.id =:memberId", TeamMember.class)
                    .setParameter("teamName", teamName)
                    .setParameter("memberId", memberId)
                    .getResultList();
        }
        else {
            return em.createQuery("SELECT tm FROM TeamMember tm WHERE tm.team.name =:teamName", TeamMember.class)
                    .setParameter("teamName", teamName)
                    .getResultList();
        }

    }

    @Override
    public List<TeamAdmin> selectTeamAdmin(String teamName, String memberId)
    {
        if(memberId != null) {
            return em.createQuery("SELECT ta FROM TeamAdmin ta WHERE ta.team.name =:teamName AND ta.member.id =:memberId", TeamAdmin.class)
                    .setParameter("teamName", teamName)
                    .setParameter("memberId", memberId)
                    .getResultList();
        }
        else
        {
            return em.createQuery("SELECT ta FROM TeamAdmin ta WHERE ta.team.name =:teamName", TeamAdmin.class)
                    .setParameter("teamName", teamName)
                    .getResultList();
        }

    }

    @Override
    public ApplicationJoinTeam selectApplication(String teamName, String memberId) {
        return em.createQuery("SELECT a FROM ApplicationJoinTeam a WHERE a.team.name =:teamName AND a.member.id=:memberId", ApplicationJoinTeam.class)
                .setParameter("teamName", teamName)
                .setParameter("memberId", memberId).getResultList().get(0);
    }
    @Override
    public void insertNewApplication(ApplicationJoinTeam application) {
        em.persist(application);
    }

    @Override
    public void insertNewTeamMember(TeamMember newTeamMember) {
        em.persist(newTeamMember);
    }

    @Override
    public void deleteApplication(ApplicationJoinTeam application) {
        em.remove(application);
    }

    @Override
    public void deleteTeamMember(String teamName, String memberId)
    {
        em.createQuery("DELETE FROM TeamMember tm WHERE tm.team.name =:teamName AND tm.member.id=:memberId", TeamMember.class)
                .setParameter("teamName", teamName)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public void deleteTeam(Team team)
    {
        em.remove(team);
    }
}
