package com.deu.football_love.dto.post;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class WritePostRequest {

  @Positive
  @NotNull
  private Long boardId;

  @NotNull
  @Size(min = 1, max = 30)
  private String title;

  @NotNull
  @Size(min = 1, max = 30)
  private String content;

  private List<MultipartFile> images;

  public WritePostRequest(Long boardId, String title, String content, List<MultipartFile> images) {
    this.boardId = boardId;
    this.title = title;
    this.content = content;
    this.images = images;
  }
}
