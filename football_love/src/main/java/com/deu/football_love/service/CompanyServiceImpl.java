package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Company;
import com.deu.football_love.domain.Member;
import com.deu.football_love.dto.AddCompanyResponse;
import com.deu.football_love.dto.CompanyDto;
import com.deu.football_love.dto.WithdrawalCompanyResponse;
import com.deu.football_love.repository.CompanyRepository;
import com.deu.football_love.repository.CompanyRepositoryImpl;
import com.deu.football_love.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public CompanyDto findCompany(Long companyId)
    {
        Company findCompany = companyRepository.selectCompany(companyId);
        if (findCompany == null)
            return null;
        return CompanyDto.from(findCompany);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDto> findCompaniesByName(String companyName)
    {
        List<CompanyDto> result = companyRepository.selectCompaniesById(companyName).stream()
                .map(c-> new CompanyDto(c.getId(),c.getName(), c.getOwner().getNumber(), c.getLocation(),c.getTel(),c.getDescription())
        ).collect(Collectors.toList());

        return result;
    }

    @Override
    @Transactional
    public AddCompanyResponse addCompany(String name, String owner, Address location, String tel, String description)
    {
        Member findMember = memberRepository.selectMemberById(owner);
        Company newCompany = companyRepository.insertCompany(new Company(name, findMember, location, tel, description));
        log.info("companyId ={}", newCompany.getId());
        return AddCompanyResponse.from(newCompany);
    }

    @Override
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