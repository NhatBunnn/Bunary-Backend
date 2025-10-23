package com.bunary.vocab.util.wordSet;

import java.time.Duration;
import java.time.Instant;

import com.bunary.vocab.model.WordSetStat;

public class WordSetStatCalculator {

    // trọng số các yếu tố
    private static final double VIEW_WEIGHT = 0.3;
    private static final double STUDY_WEIGHT = 0.4;
    private static final double RATING_WEIGHT = 0.2;
    private static final double FRESHNESS_WEIGHT = 0.3; // tăng weight để độ mới ảnh hưởng nhiều hơn
    private static final double MAX_SCORE = 100.0;

    public static double calculatePopularityScore(WordSetStat stat) {
        if (stat == null)
            return 0.0;

        // lấy giá trị an toàn, tránh null  
        long viewCount = stat.getViewCount() != null ? stat.getViewCount() : 0;
        int studyCount = stat.getStudyCount();
        double ratingAvg = stat.getRatingAvg();

        // phần log để giảm tác động khi count quá lớn
        double viewPart = Math.log(viewCount + 1) * VIEW_WEIGHT * 10; // nhân 10 để có scale tốt
        double studyPart = Math.log(studyCount + 1) * STUDY_WEIGHT * 10;
        double ratingPart = ratingAvg * 10 * RATING_WEIGHT;

        // phần độ mới (freshness)
        double freshnessPart = 0.0;
        Instant referenceTime = stat.getUpdatedAt() != null ? stat.getUpdatedAt() : stat.getCreatedAt();
        if (referenceTime != null) {
            long daysSinceUpdate = Duration.between(referenceTime, Instant.now()).toDays();
            // công thức giảm chậm theo ngày
            freshnessPart = (1.0 / (daysSinceUpdate + 1)) * FRESHNESS_WEIGHT * 10;
        }

        double total = viewPart + studyPart + ratingPart + freshnessPart;

        // giới hạn tối đa MAX_SCORE
        return Math.min(total, MAX_SCORE);
    }
}
