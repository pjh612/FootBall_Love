package com.deu.football_love.service;

import com.deu.football_love.domain.type.AuthorityType;
import com.deu.football_love.dto.team.*;

import java.util.List;

public interface TeamService {

    QueryTeamDto getTeamInfo(Long teamId);

    CreateTeamResponse createNewTeam(String creator, String teamName);

    QueryTeamDto findTeam(Long teamId);

    QueryTeamDto findTeamByName(String teamName);

    ApplicationJoinTeamDto findApplication(Long teamId, String memberId);

    void applyToTeam(Long teamId, String memberId, String message);

    AcceptApplicationResponse acceptApplication(Long teamId, String memberId);

    AuthorityType authorityCheck(Long teamId, Long memberNumber);

    void withdrawal(Long teamId, String memberId);

    DisbandmentTeamResponse disbandmentTeam(Long teamId);

    List<QueryTeamMemberDto> findTeamMember(Long teamId, Long memberNumber);

    List<QueryTeamMemberDto> findTeamMemberByMemberId(Long teamId, String memberId);

    UpdateAuthorityResponse updateAuthority(Long teamId, String memberId, AuthorityType authorityType);
}
