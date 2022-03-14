package com.deu.football_love.dto.post;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class WritePostRequest {

	@Positive
	@NotNull
	private Long boardId;

	@Positive
	@NotNull
	private Long teamId;

	@NotNull
	@Size(min = 1, max = 30)
	private String title;

	@NotNull
	@Size(min = 1, max = 30)
	private String content;

  	private List<MultipartFile> images;

	   public WritePostRequest(Long boardId, Long teamId, String title, String content, List<MultipartFile> images) {
        this.boardId = boardId;
        this.teamId = teamId;
        this.title = title;
        this.content = content;
        this.images = images;
    }

}
