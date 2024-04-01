package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SurveyRepository extends MongoRepository<Survey, String>, SurveyQueryRepository {
    List<Survey> findAllByCreatorId(String creatorId);
}
