package com.deu.football_love.repository;

import com.deu.football_love.domain.Company;

import java.util.List;

public interface CompanyRepository {
    Company selectCompany(Long companyId);
    List<Company> selectCompaniesById(String companyName);
    Company insertCompany(Company company);
    void deleteCompany(Company company);
}
