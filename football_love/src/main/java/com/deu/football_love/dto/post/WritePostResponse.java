package com.deu.football_love.dto.post;

import com.deu.football_love.domain.Post;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
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

    public WritePostResponse(Long postId, Long authorNumber, String authorId, Long boardId, LocalDateTime createDate, LocalDateTime modifyDate, String title, String content) {
        this.postId = postId;
        this.authorNumber = authorNumber;
        this.authorId = authorId;
        this.boardId = boardId;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.title = title;
        this.content = content;
    }

    public static WritePostResponse from(Post post) {
        return new WritePostResponse(post.getId(), post.getAuthor().getNumber(), post.getAuthor().getId(), post.getBoard().getId(), post.getCreatedDate()
                , post.getLastModifiedDate(), post.getTitle(), post.getContent());
    }
}
