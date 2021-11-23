package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.dto.AddCompanyResponse;
import com.deu.football_love.dto.CompanyDto;
import com.deu.football_love.dto.WithdrawalCompanyResponse;

import java.util.List;

public interface CompanyService {
    CompanyDto findCompany(Long companyId);
    AddCompanyResponse addCompany(String name, String owner, Address location, String tel, String description);
    WithdrawalCompanyResponse withdrawalCompany(Long companyId);
    List<CompanyDto> findCompaniesByName(String companyName);
}
