package com.example.demo.member.service.basic;

import com.example.demo.exception.BusinessLogicException;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberService;
import com.example.demo.utils.CustomAuthorityUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
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

    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    public BasicMemberService(MemberRepository memberRepository,
                              ApplicationEventPublisher publisher,
                              PasswordEncoder passwordEncoder,
                              CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.publisher = publisher;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    @Override
    public Member findMember(long memberId) {
        Member member = findVerifiedMember(memberId);
        return member;
    }

    @Override
    public Page<Member> findMembers(int page, int size) {
        Page<Member> members = memberRepository.findAll(PageRequest.of(
                page,
                size,
                Sort.by("memberId").descending())
        );
        return members;
    }

    // propagation, isolation
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Override
    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));
        Optional.ofNullable(member.getMemberStatus())
                .ifPresent(memberStatus -> findMember.setMemberStatus(memberStatus));
        memberRepository.save(findMember);
        return findMember;
    }

    @Override
    public void deleteMember(long memberId) {
        Member member = findVerifiedMember(memberId);
        memberRepository.delete(member);
    }

    @Override
    public Member findVerifiedMember(long memberId) {
        Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new NoSuchElementException("Member not found"));
        verifyExistsEmail(member.getEmail());
        return member;
    }

    @Override
    public void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
}
