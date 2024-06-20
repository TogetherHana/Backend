package com.togetherhana.match.entity;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.togetherhana.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="match_option")
@Table(name="match_option")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class MatchOption extends BaseEntity {

    // 빅매치 선택지 아이디
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "match_option_idx")
    private Long matchOptionIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_idx")
    private Match match;

    private String optionTitle;

}
