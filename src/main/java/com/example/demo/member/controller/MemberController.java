package com.example.demo.member.controller;

import com.example.demo.dto.MultiResponseDto;
import com.example.demo.dto.SingleResponseDto;
import com.example.demo.member.dto.MemberDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.mapper.MemberMapper;
import com.example.demo.member.service.MemberService;
import com.example.demo.stamp.Stamp;
import com.example.demo.utils.UriCreator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URL;
import java.util.List;

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

    @GetMapping("/{member_id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(memberMapper.memberToMemberResponse(member)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Member> pageMembers = memberService.findMembers(page - 1, size); // page, size를 활용해서 페이지에 해당하는 member 불러오기
        List<Member> members = pageMembers.getContent(); // pageMembers의 정보 가져오기
        return new ResponseEntity<>( // 이걸 데이터 형태로 쏜다
                new MultiResponseDto<>(memberMapper.membersToMemberResponses(members), pageMembers),
                HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity patchMember(
            @Positive @PathVariable("member-id") long memberId,
            @Valid @RequestBody MemberDto.Patch requestBody
    ) {
        requestBody.setMemberId(memberId); // MemberDto.Path 타입의 requestBody에 받은 memberId를 넣음
        Member member = memberService.updateMember(memberMapper.memberPatchToMember(requestBody)); // updateMember에 넣을 파라미터는 mapper를 활용해서 넣는다

        return new ResponseEntity<>(
                new SingleResponseDto<>(member), HttpStatus.OK
        );
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember() {
        return null;
    }
}
