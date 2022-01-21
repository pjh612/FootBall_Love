package com.deu.football_love.repository;

import com.deu.football_love.domain.ApplicationJoinTeam;
import com.deu.football_love.domain.Team;
import com.deu.football_love.domain.TeamMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TeamRepositoryImpl implements TeamRepository {

    private final EntityManager em;

    @Override
    public void insertTeam(Team team)
    {
        em.persist(team);
    }

    @Override
    public Team selectTeam(Long teamId)
    {
        return em.find(Team.class, teamId);
    }

    @Override
    public Team selectTeamByName(String teamName)
    {
        List<Team> findTeam = em.createQuery("select t from Team t where t.name = :teamName", Team.class)
                .setParameter("teamName", teamName)
                .getResultList();
        if (findTeam.size() == 0)
            return null;
        return findTeam.get(0);
    }
    @Override
    public List<TeamMember> selectTeamMember(Long teamId, Long memberNumber)
    {
        if (memberNumber != null) {
            return em.createQuery("SELECT tm FROM TeamMember tm WHERE tm.team.id =:teamId AND tm.member.number =:memberNumber", TeamMember.class)
                    .setParameter("teamId", teamId)
                    .setParameter("memberNumber", memberNumber)
                    .getResultList();
        }
        else {
            return em.createQuery("SELECT tm FROM TeamMember tm WHERE tm.team.id =:teamId", TeamMember.class)
                    .setParameter("teamId", teamId)
                    .getResultList();
        }

    }

    @Override
    public ApplicationJoinTeam selectApplication(Long teamId, String memberId) {
        return em.createQuery("SELECT a FROM ApplicationJoinTeam a WHERE a.team.id = :teamId AND a.member.id=:memberId", ApplicationJoinTeam.class)
                .setParameter("teamId", teamId)
                .setParameter("memberId", memberId).getResultList().get(0);
    }
    @Override
    public void insertNewApplication(ApplicationJoinTeam application) {
        em.persist(application);
    }

    @Override
    public TeamMember insertNewTeamMember(TeamMember newTeamMember) {
        em.persist(newTeamMember);
        return newTeamMember;
    }

    @Override
    public void deleteApplication(ApplicationJoinTeam application) {
        em.remove(application);
    }

    @Override
    public void deleteTeamMember(Long teamId, Long memberNumber)
    {
        em.createQuery("DELETE FROM TeamMember tm WHERE tm.team.id =:teamId AND tm.member.number=:memberNumber")
                .setParameter("teamId", teamId)
                .setParameter("memberNumber", memberNumber)
                .executeUpdate();
    }

    @Override
    public void deleteTeam(Team team)
    {
        em.remove(team);
    }

    @Override
    public boolean existsTeamByTeamName(String teamName) {
        Long result = em.createQuery("SELECT count(t) FROM Team t where t.name = :teamName", Long.class)
                .setParameter("teamName", teamName)
                .setMaxResults(1)
                .getSingleResult();
        return result == 1 ? true : false;
    }
}
