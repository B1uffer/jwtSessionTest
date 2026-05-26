package com.example.demo.member.service;

import com.example.demo.member.entity.Member;

public interface MemberService {
    Member createMember(Member member);
    Member updateMember(Member member);
    Member findMember(long memberId);
    void deleteMember(long memberId);

    Member findVerifiedMember(long memberId);
    void verifyExistsEmail(String email);
}
