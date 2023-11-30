package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SurveyRepository extends MongoRepository<Survey, String> {
    @Query("{ 'survey_topics': {$in: ?0} }")
    Page<Survey> findBySurveyTopicsIn(List<String> searchTopics, PageRequest pageRequest);

    List<Survey> findAllByCreatorId(String creatorId);
}
