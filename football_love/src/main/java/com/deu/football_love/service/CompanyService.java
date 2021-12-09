package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.company.QueryCompanyDto;
import com.deu.football_love.dto.company.WithdrawalCompanyResponse;

import java.util.List;

public interface CompanyService {
    QueryCompanyDto findCompany(Long companyId);

    AddCompanyResponse addCompany(String name, Long ownerNumber, Address location, String tel, String description);

    WithdrawalCompanyResponse withdrawalCompany(Long companyId);

    List<QueryCompanyDto> findCompaniesByName(String companyName);
}
