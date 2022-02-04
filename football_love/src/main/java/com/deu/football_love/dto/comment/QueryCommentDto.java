package com.deu.football_love.dto.comment;

import com.deu.football_love.domain.Comment;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
public class QueryCommentDto {

    @NotNull
    @Positive
    Long commentId;

    @NotNull
    @Positive
    Long writerNumber;

    @NotNull
    @Positive
    Long postId;

    @NotNull
    @Size(min=1,max=100)
    String comment;

    public QueryCommentDto(Comment comment) {
        this.commentId = comment.getId();
        this.writerNumber = comment.getWriter().getNumber();
        this.postId = comment.getPost().getId();
        this.comment = comment.getComment();
    }

    public static QueryCommentDto from(Comment comment)
    {
        return new QueryCommentDto(comment);
    }
}
