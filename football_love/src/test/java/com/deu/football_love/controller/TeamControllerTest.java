package com.deu.football_love.controller;

public class TeamControllerTest {
  // @Test
  // public void createTeam() throws Exception {
  //
  // Address address = new Address("busan", "guemgangro", "46233");
  // MemberJoinRequest request = new MemberJoinRequest("pjh612","123","jhjh","박진형",
  // null, address, "pjh_jn@naver.com","01021042419", MemberType.NORMAL);
  // QueryMemberDto join = memberService.join(request);
  // CreateTeamRequest createTeamRequest = new CreateTeamRequest("FC진형","반갑습니다.");
  // UserDetails userDetails = userDetailsService.loadUserByUsername(join.getId());
  // mvc.perform(MockMvcRequestBuilders.post("/api/team").with(user(userDetails))
  // .contentType("application/json")
  // .content(mapper.writeValueAsString(createTeamRequest))
  // .with(csrf())
  // ).andExpect(status().isOk())
  // .andDo(print());
  //
  // QueryTeamDto findTeam = teamService.findTeamByName(createTeamRequest.getTeamName());
  // QueryMemberDto memberA = memberService.findMemberById("pjh612");
  // assertNotNull(findTeam);
  // assertEquals(1, teamService.findTeamMember(findTeam.getId(), memberA.getNumber()).size());
  // }
}
