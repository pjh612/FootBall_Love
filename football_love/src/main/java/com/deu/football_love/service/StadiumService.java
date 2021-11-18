package com.deu.football_love.service;

import com.deu.football_love.domain.Stadium;
import com.deu.football_love.dto.AddStadiumResponse;
import com.deu.football_love.dto.RemoveStadiumResponse;
import com.deu.football_love.dto.StadiumDto;
import com.deu.football_love.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    @Transactional(readOnly = true)
    public StadiumDto findStadium(Long stadiumId)
    {
        Stadium findStadium = stadiumRepository.selectStadium(stadiumId);
        if (findStadium == null)
            return null;
        return StadiumDto.from(findStadium);
    }

    @Transactional
    public AddStadiumResponse addStadium()
    {
        Stadium newStadium = new Stadium();
        stadiumRepository.insertStadium(newStadium);
        return AddStadiumResponse.from(newStadium);
    }

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
