package com.togetherhana.member.entity;

import com.togetherhana.base.BaseEntity;
import com.togetherhana.mileage.entity.Mileage;
import com.togetherhana.sportClub.entity.MyTeam;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Member extends BaseEntity {

    // 멤버 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_idx")
    private Long memberIdx;

    private String name;

    private Long accountNumber;

    private String phoneNumber;

    private String address;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MyTeam> myTeams;

    @OneToOne
    @JoinColumn(name = "mileage_idx")
    private Mileage mileage;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<DeviceInfo> deviceInfos;
}
