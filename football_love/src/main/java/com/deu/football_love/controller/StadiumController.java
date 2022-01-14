package com.deu.football_love.controller;

import com.deu.football_love.dto.stadium.DeleteStadiumRequest;
import com.deu.football_love.dto.stadium.AddStadiumRequest;
import com.deu.football_love.dto.stadium.AddStadiumResponse;
import com.deu.football_love.dto.stadium.RemoveStadiumResponse;
import com.deu.football_love.dto.stadium.QueryStadiumDto;
import com.deu.football_love.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/stadium")
@RequiredArgsConstructor
public class StadiumController {

    private final StadiumService stadiumService;

    @GetMapping("/{stadiumId}")
    public ResponseEntity findOne(@PathVariable Long stadiumId) {
        QueryStadiumDto response = stadiumService.findStadium(stadiumId);
        if (response == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity findAll(@PathVariable Long companyId) {
        List<QueryStadiumDto> response = stadiumService.findAllStadiumByCompanyId(companyId);
        if (response == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity add(@Valid @RequestBody AddStadiumRequest request) {
        AddStadiumResponse response = stadiumService.addStadium(request.getCompanyId(), request.getType(), request.getSize(), request.getCost());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@Valid @RequestBody DeleteStadiumRequest request) {
        RemoveStadiumResponse response = stadiumService.deleteStadium(request.getStadiumId());
        return new ResponseEntity(response, HttpStatus.OK);

    }

}
