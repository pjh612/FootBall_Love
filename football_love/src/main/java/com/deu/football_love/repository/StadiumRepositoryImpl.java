package com.deu.football_love.repository;

import com.deu.football_love.domain.Stadium;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StadiumRepositoryImpl implements StadiumRepository {

    private final EntityManager em;

    @Override
    public Stadium selectStadium(Long stadiumId) {
        return em.find(Stadium.class, stadiumId);
    }

    @Override
    public List<Stadium> selectAllStadiumByCompanyId(Long companyId) {
        return em.createQuery("SELECT s FROM Stadium s WHERE s.company.id = :companyId", Stadium.class)
                .setParameter("companyId", companyId)
                .getResultList();
    }

    @Override
    public void insertStadium(Stadium stadium) {
        em.persist(stadium);
    }

    @Override
    public void deleteStadium(Stadium stadium) {
        em.remove(stadium);
    }
}
