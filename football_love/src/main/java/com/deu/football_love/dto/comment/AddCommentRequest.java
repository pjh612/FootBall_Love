package com.deu.football_love.dto.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddCommentRequest {
    @NotNull
    @Positive
    private Long postId;

    @NotNull
    @Positive
    private Long writerNumber;

    @NotNull
    @Size(min=1,max=100)
    private String comment;

    public AddCommentRequest(Long postId, Long writerNumber, String comment) {
        this.postId = postId;
        this.writerNumber = writerNumber;
        this.comment = comment;
    }
}
