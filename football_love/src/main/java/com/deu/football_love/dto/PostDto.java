package com.deu.football_love.dto;

import com.deu.football_love.domain.Board;
import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Post;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
public class PostDto {


    private Long id;

    private String authorId;

    private Long boardId;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private String title;

    private String content;

    public PostDto(Long id, String authorId, Long boardId, LocalDateTime createDate, LocalDateTime modifyDate, String title, String content) {
        this.id = id;
        this.authorId = authorId;
        this.boardId = boardId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.title = title;
        this.content = content;
    }

    public static PostDto from(Post post)
    {
       return new PostDto(post.getId(), post.getAuthor().getId(),post.getBoard().getId(), post.getCreateDate(), post.getModifyDate(), post.getTitle(), post.getContent());
    }


}
