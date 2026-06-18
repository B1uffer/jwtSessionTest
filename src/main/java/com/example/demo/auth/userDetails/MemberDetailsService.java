package com.example.demo.auth.userDetails;

import com.example.demo.exception.BusinessLogicException;
import com.example.demo.exception.ExceptionCode;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.utils.CustomAuthorityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils authorityUtils;

    public MemberDetailsService(MemberRepository memberRepository,
                                CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername username : " + username);

        Optional<Member> optionalMember = memberRepository.findByEmail(username);
        Member findMember = optionalMember
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return new MemberDetails(findMember);
    }

    private final class MemberDetails extends Member implements UserDetails {
        MemberDetails(Member member) {
            setMemberId(member.getMemberId());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setRoles(member.getRoles());
        }

        @Override
        public boolean isAccountNonExpired() {
            return UserDetails.super.isAccountNonExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
            return UserDetails.super.isAccountNonLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            // return true;
            return UserDetails.super.isCredentialsNonExpired();
        }

        @Override
        public boolean isEnabled() {
            // return true;
            return UserDetails.super.isEnabled();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles()); // member의 getRole()
        }

        @Override
        public String getUsername() {
            // return true;
            return getEmail(); // member의 getEmail()
        }
    }
}
