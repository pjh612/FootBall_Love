package com.deu.football_love.controller;

import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.stadium.DeleteStadiumRequest;
import com.deu.football_love.dto.stadium.AddStadiumRequest;
import com.deu.football_love.dto.stadium.AddStadiumResponse;
import com.deu.football_love.dto.stadium.RemoveStadiumResponse;
import com.deu.football_love.dto.stadium.QueryStadiumDto;
import com.deu.football_love.service.StadiumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/stadium")
@RequiredArgsConstructor
@Slf4j
public class StadiumController {

  private final StadiumService stadiumService;

  @GetMapping("/{stadiumId}")
  public ResponseEntity findOne(@PathVariable Long stadiumId) {
    QueryStadiumDto response = stadiumService.findStadium(stadiumId);
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @GetMapping("/{companyId}")
  public ResponseEntity findAll(@PathVariable Long companyId) {
    List<QueryStadiumDto> response = stadiumService.findAllStadiumByCompanyId(companyId);
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @PostMapping
  @PreAuthorize("hasRole('BUSINESS')")
  public ResponseEntity add(@Valid @RequestBody AddStadiumRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
    AddStadiumResponse response = stadiumService.addStadium(loginInfo.getCompanyId(), request.getType(), request.getSize(), request.getCost());
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @DeleteMapping
  @PreAuthorize("hasRole('BUSINESS')")
  public ResponseEntity delete(@Valid @RequestBody DeleteStadiumRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
    RemoveStadiumResponse response = stadiumService.deleteStadium(request.getStadiumId(), loginInfo.getCompanyId());
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity handleDataNotFoundException(final IllegalArgumentException ex) {
    log.info(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
