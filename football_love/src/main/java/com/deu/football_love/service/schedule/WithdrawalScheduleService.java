package com.deu.football_love.service.schedule;

import com.deu.football_love.repository.MemberRepository;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class WithdrawalScheduleService {

    MemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void withdrawalProcess() {
        LocalDateTime cur = LocalDateTime.now();
        Long day = 15L;
        memberRepository.deleteWithdrawalMemberByDate(cur, day);
    }

}
