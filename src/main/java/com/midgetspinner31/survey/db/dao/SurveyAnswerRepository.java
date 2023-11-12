package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.SurveyAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SurveyAnswerRepository extends MongoRepository<SurveyAnswer, String> {

    List<SurveyAnswer> findAllBySurveyId(String surveyId);
}
