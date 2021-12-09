package com.deu.football_love.dto.post;

import com.deu.football_love.domain.Post;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class QueryPostDto {


    private Long id;

    private Long authorNumber;

    private String authorId;

    private Long boardId;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private String title;

    private String content;

    public QueryPostDto(Long id, Long authorNumber, String authorId, Long boardId, LocalDateTime createDate, LocalDateTime modifyDate, String title, String content) {
        this.id = id;
        this.authorNumber =authorNumber;
        this.authorId = authorId;
        this.boardId = boardId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.title = title;
        this.content = content;
    }

    public static QueryPostDto from(Post post)
    {
       return new QueryPostDto(post.getId(), post.getAuthor().getNumber(), post.getAuthor().getId(),post.getBoard().getId(), post.getCreatedDate(), post.getLastModifiedDate(), post.getTitle(), post.getContent());
    }


}
