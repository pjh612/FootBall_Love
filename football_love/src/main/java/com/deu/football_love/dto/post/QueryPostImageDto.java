package com.deu.football_love.dto.post;

import com.deu.football_love.domain.PostImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryPostImageDto {
    String imageUri;

    public QueryPostImageDto(PostImage postImage) {
        this.imageUri = postImage.getId();
    }

    public static QueryPostImageDto from(PostImage postImage)
    {
        return new QueryPostImageDto(postImage);
    }
}
