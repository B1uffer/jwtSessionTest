package com.example.demo.member.dto;

import com.example.demo.member.entity.Member;
import com.example.demo.stamp.Stamp;
import com.example.demo.validator.NotSpace;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberDto {
    // Inner class를 활용한 dto 구현

    /**
     * Post
     */
    @Getter
    @AllArgsConstructor // TODO 테스트를 위해 추가
    public static class Post {
        @NotBlank(message = "이메일은 필수입니다.")
        @Email
        private String email;

        // 패스워드 필드 추가
        @NotBlank
        private String password;

        @NotBlank(message = "이름은 공백이 아니어야합니다.")
        private String name;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'으로 구성되어야 합니다.")
        private String phone;
    }

    /**
     * Patch
     */
    @Getter
    @AllArgsConstructor
    public static class Patch {
        private long memberId;

        @NotSpace(message = "회원 이름은 공백이 아니어야 합니다.")
        private String name;

        @NotSpace(message = "휴대폰 번호는 공백이 아니어야 합니다.")
        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'으로 구성되어야 합니다.")
        private String phone;

        private Member.MemberStatus memberStatus;

        public void setMemberId(long memberId) {
            this.memberId = memberId;
        }
    }

    /**
     * Response
     */
    @Getter
    @AllArgsConstructor
    public static class Response {
        private long memberId;
        private String email;
        private String name;
        private String phone;
        private Member.MemberStatus memberStatus;
        private Stamp stamp;

        public String getMemberStatus() {
            return memberStatus.getStatus();
        }

        public int getStamp() {
            return stamp.getStampCount();
        }
    }
}
