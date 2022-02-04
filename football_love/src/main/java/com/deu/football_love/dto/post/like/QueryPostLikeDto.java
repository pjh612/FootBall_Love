package com.deu.football_love.dto.post.like;

import com.deu.football_love.domain.PostLike;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryPostLikeDto {
    Long id;
    Long memberNumber;
    Long postId;

    public QueryPostLikeDto(PostLike pl)
    {
        this.id = pl.getId();
        this.memberNumber = pl.getMember().getNumber();
        this.postId = pl.getPost().getId();
    }

    public static QueryPostLikeDto from(PostLike pl)
    {
        return new QueryPostLikeDto(pl);
    }
}
