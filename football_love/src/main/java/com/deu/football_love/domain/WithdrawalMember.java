package com.deu.football_love.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawalMember extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "withdrawal_member_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_number", referencedColumnName = "member_number")
    private Member member;

    public WithdrawalMember(Member member) {
        this.member = member;
    }
}
