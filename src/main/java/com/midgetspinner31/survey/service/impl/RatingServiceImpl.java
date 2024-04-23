package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.InterviewRepository;
import com.midgetspinner31.survey.db.dao.InterviewSlotRepository;
import com.midgetspinner31.survey.db.dao.RatingRepository;
import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.dto.RatingInfo;
import com.midgetspinner31.survey.exception.*;
import com.midgetspinner31.survey.factory.RatingFactory;
import com.midgetspinner31.survey.service.RatingService;
import com.midgetspinner31.survey.web.request.RatingRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingServiceImpl implements RatingService {
    UserRepository userRepository;
    InterviewRepository interviewRepository;
    InterviewSlotRepository interviewSlotRepository;
    RatingRepository ratingRepository;
    RatingFactory ratingFactory;

    @Override
    @PreAuthorize("isAuthenticated()")
    public RatingInfo rate(String slotId, RatingRequest ratingRequest) {
        var user = userRepository.getCurrentUser();
        var slot = interviewSlotRepository.findById(slotId)
                .orElseThrow(InterviewSlotNotFoundException::new);
        var interview = interviewRepository.findById(slot.getInterviewId())
                .orElseThrow(InterviewNotFoundException::new);

        String targetUserId;
        if (Objects.equals(user.getId(), slot.getRespondentId())) {
            targetUserId = interview.getCreatorId();
        } else if (Objects.equals(user.getId(), interview.getCreatorId()) && slot.getRespondentId() != null) {
            targetUserId = slot.getRespondentId();
        } else {
            throw new RatingNotAllowedException();
        }

        var now = new Date();
        if (!now.after(slot.getEndDate()))
            throw new RatingTooEarlyException();

        var rating = ratingRepository.findBySlotIdAndSourceUserId(slot.getId(), user.getId())
                .orElseGet(() ->
                        ratingFactory.createRatingFrom(slot.getId(), user.getId(), targetUserId,
                                ratingFactory.createRatingInfoFrom(ratingRequest)));
        rating.setInterviewResult(ratingRequest.getInterviewResult());
        rating.setRating(ratingRequest.getRating());
        rating.setComment(ratingRequest.getComment());
        rating = ratingRepository.save(rating);

        return ratingFactory.createRatingInfoFrom(rating);
    }

    @Override
    public RatingInfo getRating(String slotId) {
        var user = userRepository.getCurrentUser();
        var slot = interviewSlotRepository.findById(slotId)
                .orElseThrow(InterviewSlotNotFoundException::new);
        var rating = ratingRepository.findBySlotIdAndSourceUserId(slot.getId(), user.getId())
                .orElseThrow(RatingNotFoundException::new);

        return ratingFactory.createRatingInfoFrom(rating);
    }

    @Override
    public double getUserAverageRating(String userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return Objects.requireNonNullElse(ratingRepository.getAverageRatingForUser(user.getId()), 0.0d);
    }
}
