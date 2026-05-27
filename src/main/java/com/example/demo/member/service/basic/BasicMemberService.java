package com.example.demo.member.service.basic;

import com.example.demo.exception.BusinessLogicException;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BasicMemberService implements MemberService {
    /**
     * 메서드 구현
     * 의존성 주입하기
     * JPA 적용하기
     * 트랜잭션 적용하기
     */
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;

    public BasicMemberService(MemberRepository memberRepository,
                              ApplicationEventPublisher publisher) {
        this.memberRepository = memberRepository;
        this.publisher = publisher;
    }

    @Override
    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        return null;
    }

    @Override
    public Member updateMember(Member member) {
        return null;
    }

    @Override
    public Member findMember(long memberId) {
        return null;
    }

    @Override
    public void deleteMember(long memberId) {

    }

    @Override
    public Member findVerifiedMember(long memberId) {
        return null;
    }

    @Override
    public void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
}
