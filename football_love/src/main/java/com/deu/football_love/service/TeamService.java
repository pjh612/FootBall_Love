package com.deu.football_love.service;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.*;

import java.util.List;

public interface TeamService {

    TeamDto getTeamInfo(Long teamId);
    CreateTeamResponse createNewTeam(String creator, String teamName);
    TeamDto findTeam(Long teamId);
    TeamDto findTeamByName(String teamName);
    ApplicationJoinTeamDto findApplication(Long teamId, String memberId);
    void applyToTeam(Long teamId, String memberId, String message);
    AcceptApplicationResponse acceptApplication(Long teamId, String memberId);
    AuthorityType authorityCheck(Long teamId, Long memberNumber);
    void withdrawal(Long teamId, String memberId);
    DisbandmentTeamResponse disbandmentTeam(Long teamId);
    List<TeamMemberDto> findTeamMember(Long teamId, Long memberNumber);
    List<TeamMemberDto> findTeamMemberByMemberId(Long teamId, String memberId);
    UpdateAuthorityResponse updateAuthority(Long teamId, String memberId, AuthorityType authorityType);
}
