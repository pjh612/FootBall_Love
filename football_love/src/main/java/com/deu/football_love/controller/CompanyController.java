package com.deu.football_love.controller;


import com.deu.football_love.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;


}
