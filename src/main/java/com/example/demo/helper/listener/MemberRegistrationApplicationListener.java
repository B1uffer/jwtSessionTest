package com.example.demo.helper.listener;

import com.example.demo.helper.email.EmailSender;
import com.example.demo.helper.event.MemberRegistrationApplicationEvent;
import com.example.demo.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Configuration
@EnableAsync
public class MemberRegistrationApplicationListener {
    private final EmailSender emailSender;
    private final MemberService memberService;

    public MemberRegistrationApplicationListener(EmailSender emailSender, MemberService memberService) {
        this.emailSender = emailSender;
        this.memberService = memberService;
    }

    @Async
    @EventListener
    public void listen(MemberRegistrationApplicationEvent event) {

    }
}
