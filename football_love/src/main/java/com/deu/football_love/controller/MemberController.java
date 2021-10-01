package com.deu.football_love.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deu.football_love.domain.Member;
import com.deu.football_love.service.MemberService;

import lombok.RequiredArgsConstructor;

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
        result.put("message", "媛��엯�릺�뿀�뒿�땲�떎.");
        return result;
    }
}
