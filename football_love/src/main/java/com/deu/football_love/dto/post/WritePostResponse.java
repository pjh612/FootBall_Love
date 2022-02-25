package com.deu.football_love.dto.post;

import java.time.LocalDateTime;
import com.deu.football_love.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class WritePostResponse {
  private Long postId;
  private Long authorNumber;
  private String authorId;
  private Long boardId;
  private LocalDateTime createDate;
  private LocalDateTime modifyDate;
  private String title;
  private String content;

  public static WritePostResponse from(Post post) {
    return new WritePostResponse(post.getId(), post.getAuthor().getNumber(),
        post.getAuthor().getId(), post.getBoard().getId(), post.getCreatedDate(),
        post.getLastModifiedDate(), post.getTitle(), post.getContent());
  }
}
