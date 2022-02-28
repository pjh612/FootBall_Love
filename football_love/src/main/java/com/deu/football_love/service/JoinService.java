package com.deu.football_love.service;

import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.member.BusinessJoinRequest;
import com.deu.football_love.dto.member.BusinessJoinResponse;
import com.deu.football_love.dto.member.MemberJoinRequest;
import com.deu.football_love.dto.member.QueryMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

  private final CompanyService companyService;
  private final MemberService memberService;

  @Transactional
  public BusinessJoinResponse businessJoin(BusinessJoinRequest req) {
    QueryMemberDto joinResponse = memberService.join(MemberJoinRequest.memberJoinRequestBuilder().id(req.getId())
        .name(req.getName()).pwd(req.getPwd()).nickname(req.getNickname()).address(req.getAddress()).birth(req.getBirth())
        .email(req.getEmail()).phone(req.getPhone()).type(req.getType()).build());
    AddCompanyResponse addCompanyResponse = companyService
        .addCompany(req.getCompanyName(), joinResponse.getNumber(), req.getLocation(), req.getTel(), req.getDescription());
    return new BusinessJoinResponse(joinResponse.getNumber(), addCompanyResponse.getCompanyId());
  }
}
