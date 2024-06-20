package com.togetherhana.member.entity;

import com.togetherhana.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="member")
@Table(name="member")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Member extends BaseEntity {

    // 멤버 아이디
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "member_idx")
    private Long memberIdx;

    // 멤버 이름
    private String name;

    // 계좌 번호
    private Long accountNumber;

    // 전화번호
    private String phoneNumber;

    // 주소
    private String address;
}
