package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Company;
import com.deu.football_love.dto.AddCompanyResponse;
import com.deu.football_love.dto.CompanyDto;
import com.deu.football_love.dto.WithdrawalCompanyResponse;
import com.deu.football_love.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public CompanyDto findCompany(Long companyId)
    {
        Company findCompany = companyRepository.selectCompany(companyId);
        if (findCompany == null)
            return null;
        return CompanyDto.from(findCompany);
    }

    @Transactional
    public AddCompanyResponse addCompany(String name, Address location, String tel, String description)
    {
        Company newCompany = new Company(name, location, tel, description);
        companyRepository.insertCompany(newCompany);
        return AddCompanyResponse.from(newCompany);
    }

    @Transactional
    public WithdrawalCompanyResponse withdrawalCompany(Long companyId)
    {
        Company findCompany = companyRepository.selectCompany(companyId);
        if(findCompany == null)
            return null;
        companyRepository.deleteCompany(findCompany);
        return new WithdrawalCompanyResponse(companyId, findCompany.getName());
    }
}
