package com.deu.football_love.dto.post;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class WritePostRequest {
    private Long authorNumber;
    private Long boardId;
    private String title;
    private String content;
    private List<MultipartFile> images;

    public WritePostRequest(Long authorNumber, Long boardId, String title, String content, List<MultipartFile> images) {
        this.authorNumber = authorNumber;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.images = images;
    }
}
