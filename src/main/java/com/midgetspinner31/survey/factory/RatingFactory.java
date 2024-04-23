package com.midgetspinner31.survey.factory;

import com.midgetspinner31.survey.db.entity.Rating;
import com.midgetspinner31.survey.dto.RatingInfo;
import com.midgetspinner31.survey.web.request.RatingRequest;
import org.springframework.stereotype.Component;

@Component
public class RatingFactory {
    public RatingInfo createRatingInfoFrom(RatingRequest ratingRequest) {
        return new RatingInfo(
                ratingRequest.getInterviewResult(),
                ratingRequest.getRating(),
                ratingRequest.getComment()
        );
    }

    public RatingInfo createRatingInfoFrom(Rating rating) {
        return new RatingInfo(
                rating.getInterviewResult(),
                rating.getRating(),
                rating.getComment()
        );
    }

    public Rating createRatingFrom(String slotId, String sourceUserId, String targetUserId, RatingInfo ratingInfo) {
        return Rating.builder()
                .slotId(slotId)
                .sourceUserId(sourceUserId)
                .targetUserId(targetUserId)
                .interviewResult(ratingInfo.getInterviewResult())
                .rating(ratingInfo.getRating())
                .comment(ratingInfo.getComment())
                .build();
    }
}
