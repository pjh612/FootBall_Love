package com.deu.football_love.dto.post.like;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikePostResponse {
    Boolean success;
    String message;

    public LikePostResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
