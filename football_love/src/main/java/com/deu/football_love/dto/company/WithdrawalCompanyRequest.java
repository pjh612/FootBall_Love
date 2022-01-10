package com.deu.football_love.dto.company;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class WithdrawalCompanyRequest {
	@NotNull
	@Positive
    private Long companyId;
}
