package com.deu.football_love.dto.cloud_storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UploadImageRequest {
    private MultipartFile file;

    public UploadImageRequest(MultipartFile file) {
        this.file = file;
    }
}
