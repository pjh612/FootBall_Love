package com.deu.football_love.service;

import com.deu.football_love.domain.Address;
import com.deu.football_love.domain.Company;
import com.deu.football_love.domain.Member;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.company.QueryCompanyDto;
import com.deu.football_love.dto.company.UpdateCompanyRequest;
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
@Transactional
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final MemberRepository memberRepository;

  @Transactional(readOnly = true)
  public QueryCompanyDto findCompany(Long companyId) {
    Company findCompany = companyRepository.findById(companyId)
        .orElseThrow(() -> new IllegalArgumentException("no such company data."));
    return QueryCompanyDto.from(findCompany);
  }

  @Transactional(readOnly = true)
  public QueryCompanyDto findByMemberNumber(Long memberNumber) {
    QueryCompanyDto findCompany = companyRepository.findQueryCompanyDto(memberNumber)
        .orElseThrow(() -> new IllegalArgumentException("no such company data."));
    return findCompany;
  }

  @Transactional(readOnly = true)
  public List<QueryCompanyDto> findCompaniesByName(String companyName) {
    List<QueryCompanyDto> result = companyRepository.findAllByName(companyName).stream()
        .map(c -> new QueryCompanyDto(c.getId(), c.getName(), c.getOwner().getNumber(),
            c.getLocation(), c.getTel(), c.getDescription())
        ).collect(Collectors.toList());
    return result;
  }

  public AddCompanyResponse addCompany(String name, Long ownerNumber, Address location, String tel,
      String description) {
    Member findMember = memberRepository.findById(ownerNumber)
        .orElseThrow(() -> new IllegalArgumentException("no such member data."));
    if (companyRepository.existsByMemberNumber(ownerNumber)) {
      throw new IllegalArgumentException("already have company.");
    }
    Company newCompany = companyRepository
        .save(new Company(name, findMember, location, tel, description));
    findMember.setCompany(newCompany);
    return AddCompanyResponse.from(newCompany);
  }


  public WithdrawalCompanyResponse withdrawalCompany(Long companyId) {
    Company findCompany = companyRepository.findById(companyId)
        .orElseThrow(() -> new IllegalArgumentException("no such company data."));
    companyRepository.delete(findCompany);
    return new WithdrawalCompanyResponse(companyId, findCompany.getName());
  }

  public void updateCompany(UpdateCompanyRequest request) {
    Company company = companyRepository.findById(request.getCompanyId())
        .orElseThrow(() -> new IllegalArgumentException("no such company data."));
    company.update(request);
  }
}
