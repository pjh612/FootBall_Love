package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Company;
import com.deu.football_love.domain.Member;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.company.QueryCompanyDto;
import com.deu.football_love.dto.company.WithdrawalCompanyResponse;
import com.deu.football_love.repository.CompanyRepository;
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
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public QueryCompanyDto findCompany(Long companyId) {
        Company findCompany = companyRepository.selectCompany(companyId);
        if (findCompany == null)
            return null;
        return QueryCompanyDto.from(findCompany);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QueryCompanyDto> findCompaniesByName(String companyName) {
        List<QueryCompanyDto> result = companyRepository.selectCompaniesById(companyName).stream()
                .map(c -> new QueryCompanyDto(c.getId(), c.getName(), c.getOwner().getNumber(), c.getLocation(), c.getTel(), c.getDescription())
                ).collect(Collectors.toList());
        return result;
    }

    @Override
    @Transactional
    public AddCompanyResponse addCompany(String name, Long ownerNumber, Address location, String tel, String description) {
        Member findMember = memberRepository.selectMember(ownerNumber);
        Company newCompany = companyRepository.insertCompany(new Company(name, findMember, location, tel, description));
        findMember.setCompany(newCompany);
        return AddCompanyResponse.from(newCompany);
    }

    @Override
    @Transactional
    public WithdrawalCompanyResponse withdrawalCompany(Long companyId) {
        Company findCompany = companyRepository.selectCompany(companyId);
        if (findCompany == null)
            return null;
        companyRepository.deleteCompany(findCompany);
        return new WithdrawalCompanyResponse(companyId, findCompany.getName());
    }
}
