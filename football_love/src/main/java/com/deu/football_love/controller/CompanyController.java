package com.deu.football_love.controller;


import com.deu.football_love.dto.company.AddCompanyRequest;
import com.deu.football_love.dto.company.AddCompanyResponse;
import com.deu.football_love.dto.company.WithdrawalCompanyRequest;
import com.deu.football_love.dto.company.WithdrawalCompanyResponse;
import com.deu.football_love.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity add(@RequestBody AddCompanyRequest request)
    {
        AddCompanyResponse response = companyService.addCompany(request.getCompanyName(), request.getOwnerNumber(), request.getLocation(), request.getTel(), request.getDescription());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity withdrawal(@RequestBody WithdrawalCompanyRequest request)
    {
        WithdrawalCompanyResponse response = companyService.withdrawalCompany(request.getCompanyId());
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
