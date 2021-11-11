package com.deu.football_love.dto;

import com.deu.football_love.domain.Post;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


public class WritePostResponse {
    private String Author;
    private Long boardId;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String title;
    private String content;

    public WritePostResponse(String author, Long boardId, LocalDateTime createDate, LocalDateTime modifyDate, String title, String content) {
        Author = author;
        this.boardId = boardId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.title = title;
        this.content = content;
    }

    public static WritePostResponse from(Post post)
    {
        return new WritePostResponse(post.getAuthor().getId(), post.getBoard().getId(), post.getCreateDate()
                , post.getModifyDate(), post.getTitle(), post.getContent());
    }
}
