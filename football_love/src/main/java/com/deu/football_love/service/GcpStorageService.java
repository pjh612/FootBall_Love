package com.deu.football_love.service;

import com.deu.football_love.domain.Member;
import com.deu.football_love.domain.Team;
import com.deu.football_love.repository.MemberRepository;
import com.deu.football_love.repository.TeamRepository;
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

  @Value("${spring.cloud.gcp.storage.team-profile-bucket-name}")
  private String teamProfileBucketName;

  private final MemberRepository memberRepository;

  private final TeamRepository teamRepository;

  public String updateProfileImg(MultipartFile file, String userId) throws IOException {
    Member findMember = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("no such member data."));
    String imgName = findMember.getProfileImgUri() != null ? findMember.getProfileImgUri() : UUID.randomUUID().toString();
    BlobInfo blobInfo = BlobInfo.newBuilder(profileBucketName, imgName)
        .setContentType(file.getContentType())
        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
        .build();
    try (WriteChannel writer = storage.writer(blobInfo, Storage.BlobWriteOption.md5Match())) {
      ByteStreams.copy(new ByteArrayInputStream(file.getBytes()), Channels.newOutputStream(writer));
    }
    findMember.updateProfileImgUri(imgName);
    return findMember.getProfileImgUri();
  }

  public String updateTeamProfileImg(MultipartFile file, Long teamId) throws IOException {

    Team findTeam = teamRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("no such team data."));
    String imgName = findTeam.getProfileImgUri() != null ? findTeam.getProfileImgUri() : UUID.randomUUID().toString();
    BlobInfo blobInfo = BlobInfo.newBuilder(teamProfileBucketName, imgName)
        .setContentType(file.getContentType())
        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
        .build();
    try (WriteChannel writer = storage.writer(blobInfo, Storage.BlobWriteOption.md5Match())) {
      ByteStreams.copy(new ByteArrayInputStream(file.getBytes()), Channels.newOutputStream(writer));
    }
    return imgName;
  }


  public String updatePostImg(MultipartFile file) throws IOException {
    String imgName = UUID.randomUUID().toString();
    BlobInfo blobInfo = BlobInfo.newBuilder(postBucketName, imgName)
        .setContentType(file.getContentType())
        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
        .build();
    try (WriteChannel writer = storage.writer(blobInfo, Storage.BlobWriteOption.md5Match())) {
      ByteStreams.copy(new ByteArrayInputStream(file.getBytes()), Channels.newOutputStream(writer));
    }
    return imgName;
  }


}
