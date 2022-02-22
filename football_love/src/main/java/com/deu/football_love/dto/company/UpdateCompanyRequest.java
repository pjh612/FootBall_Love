package com.deu.football_love.dto.company;

import com.deu.football_love.domain.Address;
import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCompanyRequest {

  @Size(min=1,max=36)
  private String name;

  private Address location;

  @Size(min=1,max=13)
  @Pattern(regexp = "^(01[0|1|6|7|8|9])-(\\d{3,4})-(\\d{4})$")
  private String tel;

  @Size(min=1,max=100)
  private String description;


}
