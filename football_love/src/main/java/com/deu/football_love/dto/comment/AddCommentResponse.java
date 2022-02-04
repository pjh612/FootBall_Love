package com.deu.football_love.dto.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddCommentResponse {

    Boolean success;

    Long commentId;

    public AddCommentResponse(boolean success, Long commentId) {
        this.success = success;
        this.commentId = commentId;
    }
}
