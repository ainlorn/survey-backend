package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.RatingInfo;
import com.midgetspinner31.survey.web.request.RatingRequest;

public interface RatingService {
    RatingInfo rate(String slotId, RatingRequest ratingRequest);
    RatingInfo getRating(String slotId);
    double getUserAverageRating(String userId);
}
