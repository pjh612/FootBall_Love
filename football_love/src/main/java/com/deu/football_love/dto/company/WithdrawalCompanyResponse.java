package com.deu.football_love.dto.company;

public class WithdrawalCompanyResponse {
    private Long companyId;
    private String companyName;

    public WithdrawalCompanyResponse(Long companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }
}