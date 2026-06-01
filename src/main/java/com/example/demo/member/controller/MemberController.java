package com.example.demo.member.controller;

import com.example.demo.member.dto.MemberDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.mapper.MemberMapper;
import com.example.demo.member.service.MemberService;
import com.example.demo.stamp.Stamp;
import com.example.demo.utils.UriCreator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URL;

@Slf4j
@Validated
@RestController
@RequestMapping("/v11/members")
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/v11/members";
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) { // ResponseEntity로 반환, 매개변수는 RequestBody로 받음
        Member member = memberMapper.memberPostToMember(requestBody);
        member.setStamp(new Stamp()); // 새로운 Stamp를 여기에서 생성함

        memberService.createMember(member);
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, member.getMemberId()); // 신규 회원의 URI 생성
        return ResponseEntity.created(location).build();
    }
}
