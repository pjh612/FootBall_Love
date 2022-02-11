package com.deu.football_love.dto.team;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class UpdateTeamProfileRequest {
    @NotNull
    @Positive
    Long teamId;
    MultipartFile profileImg;

    @NotNull
    @Size(min = 1, max =400)
    String introduce;

    public UpdateTeamProfileRequest(Long teamId, MultipartFile profileImg, String introduce) {
        this.teamId = teamId;
        this.profileImg = profileImg;
        this.introduce = introduce;
    }
}
