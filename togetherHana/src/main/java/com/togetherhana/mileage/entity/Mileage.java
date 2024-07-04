package com.togetherhana.mileage.entity;

import com.togetherhana.base.BaseEntity;
import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "mileage")
@Table(name = "mileage")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ToString
public class Mileage extends BaseEntity {

    // 마일리지 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mileage_idx")
    private Long mileageIdx;

    // 보유액
    private Long amount;

    public long withdraw(long usedAmount) {
        if (this.amount < usedAmount) {
            throw new BaseException(ErrorType.NOT_ENOUGH_MILEAGE_AMOUNT);
        }
        return amount -= usedAmount;
    }

    public long plus(long amount) {
        return this.amount += amount;

    }
}
