package com.deu.football_love.dto.post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.deu.football_love.domain.Post;
import com.deu.football_love.dto.BaseDto;
import com.deu.football_love.dto.comment.QueryCommentDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryPostDto extends BaseDto {


  private Long id;

  private Long authorNumber;

  private String authorId;

  private Long boardId;

  private String title;

  private String content;

  private Long likeCount;

  private List<QueryCommentDto> comments = new ArrayList<>();

  private List<QueryPostImageDto> postImages = new ArrayList<>();

  private QueryPostDto(Post post) {
    this.id = post.getId();
    this.authorNumber = post.getAuthor().getNumber();
    this.authorId = post.getAuthor().getId();
    this.boardId = post.getBoard().getId();
    this.setCreatedDate(post.getCreatedDate());
    this.setLastModifiedDate(post.getLastModifiedDate());
    this.title = post.getTitle();
    this.content = post.getContent();
    this.comments = post.getComments().stream().map(comment -> new QueryCommentDto(comment))
        .collect(Collectors.toList());
    this.setCreatedBy(post.getCreatedBy());
    this.setLastModifiedBy(post.getLastModifiedBy());
    this.setCreatedDate(post.getCreatedDate());
    this.setLastModifiedDate(post.getLastModifiedDate());
  }

  public static QueryPostDto from(Post post) {
    return new QueryPostDto(post);
  }


}
