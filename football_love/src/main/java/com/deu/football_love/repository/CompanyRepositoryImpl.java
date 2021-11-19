package com.deu.football_love.repository;

import com.deu.football_love.domain.Company;
import com.deu.football_love.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {
    private final EntityManager em;

    @Override
    public Company selectCompany(Long companyId)
    {
        return em.find(Company.class, companyId);
    }

    @Override
    public List<Company> selectCompaniesById(String companyName)
    {
        return em.createQuery("select c from Company c where c.name=:companyName",Company.class)
                .setParameter("companyName",companyName)
                .getResultList();
    }

    @Override
    public Company insertCompany(Company company)
    {
        em.persist(company);
        return company;
    }

    @Override
    public void deleteCompany(Company company)
    {
        em.remove(company);
    }
}
