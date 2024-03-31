package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.InterviewSlot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface InterviewSlotRepository extends MongoRepository<InterviewSlot, String> {
    List<InterviewSlot> findAllByInterviewId(String interviewId);

    @Query("{ 'interviewId': ?0, 'startDate': {$gte: new Date()}, 'respondentId': null }")
    List<InterviewSlot> findAllFreeByInterviewId(String interviewId);

    List<InterviewSlot> findAllByRespondentId(String respondentId);

    void deleteAllByInterviewId(String interviewId);
}
