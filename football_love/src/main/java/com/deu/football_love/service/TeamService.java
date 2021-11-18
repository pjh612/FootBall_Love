package com.deu.football_love.service;

import com.deu.football_love.domain.*;
import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.*;

import java.util.List;

public interface TeamService {

    TeamDto getTeamInfo(String teamName);
    CreateTeamResponse createNewTeam(MemberDto member, String teamName);
    TeamDto findTeam(String teamName);
    ApplicationJoinTeamDto findApplication(String teamName, String memberId);
    void applyToTeam(String teamName, String memberId, String message);
    AcceptApplicationResponse acceptApplication(String teamName, String memberId);
    AuthorityType authorityCheck(String teamName, String memberId);
    void withdrawal(String teamName, String memberId);
    DisbandmentTeamResponse disbandmentTeam(String teamName);
    List<TeamMemberDto> findTeamMember(String teamName, String memberId);
    UpdateAuthorityResponse updateAuthority(String teamName, String memberId, AuthorityType authorityType);
}
