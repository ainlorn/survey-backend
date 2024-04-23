package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.Rating;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RatingRepository extends MongoRepository<Rating, String> {
    Optional<Rating> findBySlotIdAndSourceUserId(String slotId, String sourceUserId);

    @Aggregation(pipeline = {
            "{ $match: { 'targetUserId': ?0 } }",
            "{ $group: { _id: null, average: { $avg: \"$rating\" } } }"
    })
    Double getAverageRatingForUser(String targetUserId);
}
