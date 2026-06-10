package com.example.demo.member.entity;

import com.example.demo.order.entity.Order;
import com.example.demo.stamp.Stamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    /**
     * 이메일
     */
    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    /**
     * 이름
     */
    @Column(length = 100, nullable = false)
    private String name;

    /**
     * 전화번호
     */
    @Column(nullable = false, unique = true, length = 13)
    private String phone;

    /**
     * 멤버의 상태
     */
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    /**
     * Member가 Order의 상태를 알고 있음
     */
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    /**
     * 스탬프
     */
    @OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Stamp stamp;


    // (2) 추가
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    /**
     * 생성자
     */
    public Member(String email) {
        this.email = email;
    }

    public Member(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    /**
     * setter
     */
    public void setOrder(Order order) {
        this.orders.add(order);
        if(order.getMember() != this) {
            order.setMember(this);
        }
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
        if(stamp.getMember() != this) {
            stamp.setMember(this);
        }
    }

    /**
     * enum MemberStatus
     */
    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
