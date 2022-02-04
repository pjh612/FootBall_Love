package com.deu.football_love.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "PostLike", uniqueConstraints = {@UniqueConstraint(
        name = "POST_LIKE_UNIQUE", columnNames = {"post_id", "member_number"}
)})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike extends BaseEntity {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_number")
    Member member;

    public PostLike(Post post, Member member) {
        post.getPostLikes().add(this);
        this.post = post;
        this.member = member;
    }
}
