package com.deu.football_love.service;


import com.deu.football_love.domain.Company;
import com.deu.football_love.domain.Stadium;
import com.deu.football_love.domain.type.StadiumFieldType;
import com.deu.football_love.dto.stadium.AddStadiumResponse;
import com.deu.football_love.dto.stadium.RemoveStadiumResponse;
import com.deu.football_love.dto.stadium.QueryStadiumDto;
import com.deu.football_love.repository.CompanyRepository;
import com.deu.football_love.repository.StadiumRepository;
import com.deu.football_love.repository.StadiumRepository.StadiumId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public QueryStadiumDto findStadium(Long stadiumId) {
        Stadium findStadium = stadiumRepository.findById(stadiumId).orElseThrow(()-> new IllegalArgumentException("no such stadium data"));
        return QueryStadiumDto.from(findStadium);
    }

    public List<QueryStadiumDto> findAllStadiumByCompanyId(Long companyId) {
        List<QueryStadiumDto> collect = stadiumRepository.selectAllStadiumByCompanyId(companyId).stream().map(s -> new QueryStadiumDto(s.getId(), s.getType(), s.getSize(), s.getCost())).collect(Collectors.toList());
        return collect;
    }

    public List<StadiumId> findAllStadiumIdsByCompanyId(Long companyId) {
        List<StadiumId> stadiumIdList = stadiumRepository.findAllIdsByCompanyId(companyId);
        return stadiumIdList;
    }
        public List<Stadium> findAllStadiumsByCompanyId(Long companyId) {
            List<Stadium> stadiums = stadiumRepository.findAllByCompanyId(companyId);
            return stadiums;
    }

  public AddStadiumResponse addStadium(Long companyId, StadiumFieldType type, String size, Long cost) {
    Company findCompany = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("no such company data."));
    Stadium newStadium = new Stadium(type, size, cost, findCompany);
    findCompany.getStadiums().add(newStadium);
    stadiumRepository.save(newStadium);
    return AddStadiumResponse.from(newStadium);
  }

    public RemoveStadiumResponse deleteStadium(Long stadiumId) {
        Stadium findStadium = stadiumRepository.findById(stadiumId).orElseThrow(()-> new IllegalArgumentException("no such stadium data"));
        stadiumRepository.delete(findStadium);
        return new RemoveStadiumResponse(stadiumId);
    }
}
