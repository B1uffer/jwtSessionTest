package com.example.demo.helper.event;

import com.example.demo.member.entity.Member;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class MemberRegistrationApplicationEvent extends ApplicationEvent {
    private Member member;

    public MemberRegistrationApplicationEvent(Object source, Member clock) {
        super(source);
        this.member = member;
    }

    /**
     * getter
     */
    public Member getMember() {
        return member;
    }
}
