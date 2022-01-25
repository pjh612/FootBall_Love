package com.deu.football_love.dto.post;

import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class QueryPostDto extends BaseDto {


    private Long id;

    private Long authorNumber;

    private String authorId;

    private Long boardId;

    private Long teamId;

    private String title;

    private String content;

    private List<QueryPostImageDto> postImages = new ArrayList<>();

   /* public QueryPostDto(Long id, Long authorNumber, String authorId, Long boardId, LocalDateTime createdDate, LocalDateTime lastModifiedDate, String title, String content) {
        this.id = id;
        this.authorNumber =authorNumber;
        this.authorId = authorId;
        this.boardId = boardId;
        this.setLastModifiedDate(lastModifiedDate);
        this.setCreatedDate(createdDate);
        this.set
        this.title = title;
        this.content = content;
    }*/

    public QueryPostDto(Post post) {
        this.id = post.getId();
        this.authorNumber =post.getAuthor().getNumber();
        this.authorId = post.getAuthor().getId();
        this.boardId = post.getBoard().getId();
        this.teamId = post.getBoard().getTeam().getId();
        this.setCreatedDate(post.getCreatedDate());
        this.setLastModifiedDate(post.getLastModifiedDate());
        this.setCreatedBy(post.getCreatedBy());
        this.setLastModifiedBy(post.getLastModifiedBy());
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    public static QueryPostDto from(Post post)
    {
       return new QueryPostDto(post);
    }


}
