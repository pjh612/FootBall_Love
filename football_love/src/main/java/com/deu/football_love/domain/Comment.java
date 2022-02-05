package com.deu.football_love.domain;

import com.deu.football_love.dto.comment.UpdateCommentRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "column_id")
    private Long id;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "member_number")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    private String comment;

    public Comment(Post post, Member writer, String comment) {
        this.post = post;
        this.writer = writer;
        this.comment = comment;
        writer.getComments().add(this);
        post.getComments().add(this);
    }

    public void updateComment(UpdateCommentRequest request)
    {
        this.comment = request.getComment();
    }

}
