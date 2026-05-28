package com.example.demo.helper.event;

import com.example.demo.member.entity.Member;
import org.springframework.context.ApplicationEvent;

public class MemberRegistrationApplicationEvent extends ApplicationEvent {
    private Member member;

    public MemberRegistrationApplicationEvent(Object source, Member member) {
        super(source);
        this.member = member;
    }
    /**
     * Getter
     */
    public Member getMember() {
        return this.member;
    }
}
