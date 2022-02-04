package com.deu.football_love.dto.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCommentRequest {
    @NotNull
    @Positive
    Long commentId;

    @NotNull
    @Size(min=1,max=100)
    String comment;

    public UpdateCommentRequest(Long commentId, String comment) {
        this.commentId = commentId;
        this.comment = comment;
    }
}
