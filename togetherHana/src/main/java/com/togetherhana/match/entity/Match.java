package com.togetherhana.match.entity;

import com.togetherhana.base.BaseEntity;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Match extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_idx")
    private Long id;

    private String matchTitle;
    private LocalDateTime deadline;
    private Integer fine;
    private Boolean isPlaying;

    @ManyToOne
    @JoinColumn(name = "sharing_account_idx")
    private SharingAccount sharingAccount;

    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
    private List<MatchParticipant> matchParticipants;

}
