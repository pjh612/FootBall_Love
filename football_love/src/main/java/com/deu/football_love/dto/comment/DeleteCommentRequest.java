package com.deu.football_love.dto.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteCommentRequest {
    private Long commentId;

    public DeleteCommentRequest(Long commentId) {
        this.commentId = commentId;
    }
}
