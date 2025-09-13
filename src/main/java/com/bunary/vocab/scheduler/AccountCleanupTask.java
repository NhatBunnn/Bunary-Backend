package com.bunary.vocab.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bunary.vocab.service.user.IUserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AccountCleanupTask {
    private final IUserService userService;

    // @Scheduled(cron = "0 0 3 * * ?", zone = "Asia/Ho_Chi_Minh")
    @Transactional
    public long removeAccount() {
        return this.userService.deleteByIsEmailVerified(false);
    }

}
