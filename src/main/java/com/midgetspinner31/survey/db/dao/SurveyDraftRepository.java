package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.SurveyDraft;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SurveyDraftRepository extends MongoRepository<SurveyDraft, String> {
}
