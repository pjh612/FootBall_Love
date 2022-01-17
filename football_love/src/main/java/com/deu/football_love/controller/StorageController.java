package com.deu.football_love.controller;

import com.deu.football_love.dto.auth.LoginInfo;
import com.deu.football_love.dto.cloud_storage.UploadImageRequest;
import com.deu.football_love.service.GcpStorageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class StorageController {

    private final GcpStorageService gcpStorageService;


    @SneakyThrows
    @PostMapping(value = "/api/upload")
    public String upload(UploadImageRequest request, @AuthenticationPrincipal LoginInfo loginInfo) {
        log.info("id = {}", loginInfo.getId());
        String imgUri = gcpStorageService.updateProfileImg(request.getFile(), loginInfo.getId());
        return imgUri;
    }


    @GetMapping(value = "/api/upload")
    public String get(UploadImageRequest request) {
       return "mapping test";

    }

}
