package com.example.demo.member.service;

import com.example.demo.member.entity.Member;
import org.springframework.data.domain.Page;

public interface MemberService {
    Member createMember(Member member);
    Member findMember(long memberId);
    Page<Member> findMembers(int page, int size);
    Member updateMember(Member member);
    void deleteMember(long memberId);

    Member findVerifiedMember(long memberId);
    void verifyExistsEmail(String email);
}
