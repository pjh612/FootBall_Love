package com.deu.football_love.controller;

import com.deu.football_love.domain.Member;
import com.deu.football_love.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    @PostMapping("/join")
    public Map<String,Object> join(@RequestBody Member member)
    {
        HashMap<String, Object> result = new HashMap<>();
        memberService.join(member);
        result.put("status", 200);
        result.put("message", "가입되었습니다.");
        return result;
    }
}
