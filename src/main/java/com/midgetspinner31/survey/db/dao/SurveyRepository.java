package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SurveyRepository extends MongoRepository<Survey, String> {

}
