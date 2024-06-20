package com.togetherhana.sharingAccount.entity;

import com.togetherhana.base.BaseEntity;
import com.togetherhana.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity(name="sharing_member")
@Table(name="sharing_member")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class SharingMember extends BaseEntity {

    // 모임통장 아이디
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "sharing_member_idx")
    private Long sharingMemberIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sharing_account_idx")
    private SharingAccount sharingAccount;

    @OneToOne
    @JoinColumn(name = "member_idx")
    private Member member;

    // ROLE
    @ColumnDefault("false")
    private Boolean isLeader;

}
