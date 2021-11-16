package com.deu.football_love.repository;

import com.deu.football_love.domain.Company;
import com.deu.football_love.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {
    private final EntityManager em;

    public Company selectCompany(Long companyId)
    {
        return em.find(Company.class, companyId);
    }

    public void insertCompany(Company company)
    {
        em.persist(company);
    }

    public void deleteCompany(Company company)
    {
        em.remove(company);
    }
}
