package com.bunary.vocab.user.scheduler;

import org.springframework.stereotype.Component;

@Component
public class UserStatsSnapshotScheduler {
    // @Scheduled(cron = "0 0 0 * * *") // chạy mỗi ngày 0h
    // public void snapshot7DaysJob() {
    // List<Long> userIds = userRepository.findAllUserIds();
    // userIds.forEach(id -> createSnapshot(id, 7));
    // }

}
