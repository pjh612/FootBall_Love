package com.deu.football_love.controller;


import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.company.AddCompanyRequest;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.company.UpdateCompanyRequest;
import com.deu.football_love.dto.company.WithdrawalCompanyRequest;
import com.deu.football_love.dto.company.WithdrawalCompanyResponse;
import com.deu.football_love.service.CompanyService;
import com.deu.football_love.service.MatchService;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

  private final CompanyService companyService;
  private final MatchService matchService;

  @PostMapping
  @PreAuthorize("hasRole('BUSINESS')")
  public ResponseEntity add(@AuthenticationPrincipal LoginInfo loginInfo,
      @Valid @RequestBody AddCompanyRequest request) {
    AddCompanyResponse response = companyService
        .addCompany(request.getCompanyName(), loginInfo.getNumber(), request.getLocation(),
            request.getTel(), request.getDescription());
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @DeleteMapping
  @PreAuthorize("hasRole('BUSINESS')")
  public ResponseEntity withdrawal(@Valid @RequestBody WithdrawalCompanyRequest request) {
    WithdrawalCompanyResponse response = companyService.withdrawalCompany(request.getCompanyId());
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @GetMapping("/match")
  @PreAuthorize("hasRole('BUSINESS')")
  public ResponseEntity getCompanyMatchList(@AuthenticationPrincipal LoginInfo loginInfo) {
    return new ResponseEntity(matchService.findAllByCompanyId(loginInfo.getNumber()), HttpStatus.OK);
  }

  @PutMapping
  @PreAuthorize("hasRole('BUSINESS')")
  public ResponseEntity update(@RequestBody UpdateCompanyRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
    companyService.updateCompany(loginInfo.getCompanyId(), request);
    return new ResponseEntity(HttpStatus.OK);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity handleDataNotFoundException(final IllegalArgumentException ex) {
    log.info(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
