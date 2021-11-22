package com.deu.football_love.service;


import com.deu.football_love.domain.Company;
import com.deu.football_love.domain.Stadium;
import com.deu.football_love.dto.AddStadiumResponse;
import com.deu.football_love.dto.RemoveStadiumResponse;
import com.deu.football_love.dto.StadiumDto;
import com.deu.football_love.repository.CompanyRepository;
import com.deu.football_love.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StadiumServiceImpl implements StadiumService {

    private final StadiumRepository stadiumRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional(readOnly = true)
    public StadiumDto findStadium(Long stadiumId)
    {
        Stadium findStadium = stadiumRepository.selectStadium(stadiumId);
        if (findStadium == null)
            return null;
        return StadiumDto.from(findStadium);
    }

    @Override
    @Transactional
    public AddStadiumResponse addStadium(Long companyId, String type, String size, Long cost)
    {
        Company findCompany = companyRepository.selectCompany(companyId);
        Stadium newStadium = new Stadium(type, size, cost, findCompany);
        findCompany.getStadiums().add(newStadium);
        stadiumRepository.insertStadium(newStadium);
        return AddStadiumResponse.from(newStadium);
    }

    @Override
    @Transactional
    public RemoveStadiumResponse removeStadium(Long stadiumId)
    {
        Stadium findStadium = stadiumRepository.selectStadium(stadiumId);
        if(findStadium == null)
            return null;
        stadiumRepository.deleteStadium(findStadium);
        return new RemoveStadiumResponse(stadiumId);
    }
}
