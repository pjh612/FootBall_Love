package com.deu.football_love.service;

import com.deu.football_love.domain.Member;
import com.deu.football_love.repository.MemberRepository;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.common.io.ByteStreams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GcpStorageService {

    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.profileBucketName}")
    private String profileBucketName;

    @Value("${spring.cloud.gcp.storage.postBucketName}")
    private String postBucketName;

    private final MemberRepository memberRepository;

    public String updateProfileImg(MultipartFile file, String userId) throws IOException {
        InputStream targetStream = new ByteArrayInputStream(file.getBytes());
        Member findMember = memberRepository.selectMemberById(userId);
        String imgName;
        if (findMember.getProfileImgUri() != null)
            imgName = findMember.getProfileImgUri();
        else
            imgName = UUID.randomUUID().toString();
        BlobInfo blobInfo = BlobInfo.newBuilder(profileBucketName, imgName)
                .setContentType(file.getContentType())
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                .build();
        try (WriteChannel writer = storage.writer(blobInfo, Storage.BlobWriteOption.md5Match())) {
            ByteStreams.copy(targetStream, Channels.newOutputStream(writer));
        }
        findMember.updateProfileImgUri(imgName);
        return findMember.getProfileImgUri();
    }

    public String updatePostImg(MultipartFile file) throws IOException {
        InputStream targetStream = new ByteArrayInputStream(file.getBytes());
        String imgName = UUID.randomUUID().toString();
        BlobInfo blobInfo = BlobInfo.newBuilder(postBucketName, imgName)
                .setContentType(file.getContentType())
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                .build();
        try (WriteChannel writer = storage.writer(blobInfo, Storage.BlobWriteOption.md5Match())) {
            ByteStreams.copy(targetStream, Channels.newOutputStream(writer));
        }
        return imgName;
    }


}
