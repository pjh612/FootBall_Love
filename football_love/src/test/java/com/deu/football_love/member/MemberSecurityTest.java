package com.deu.football_love.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
@SpringBootTest
public class MemberSecurityTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

/*
    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
*/


    @Test
    @WithMockUser(roles = "USER")
    public void Posts_등록된다() throws Exception {
 /*       String title = "title";
        String content = "content";
         requestDto = Login

        String url = "http://localhost:" + port + "/api/v1/posts";

        // mvc.perform
        // 생성된 MockMvc를 통해 API를 테스트한다.
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                // 본문(Body) 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환한다.
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);*/

    }

}
