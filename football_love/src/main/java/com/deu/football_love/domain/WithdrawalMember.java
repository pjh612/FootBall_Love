package com.deu.football_love.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class WithdrawalMember {

    @Id
    @GeneratedValue
    @Column(name = "withdrawal_member_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    @Column(name= "withdrawal_date")
    private LocalDateTime date;
}
