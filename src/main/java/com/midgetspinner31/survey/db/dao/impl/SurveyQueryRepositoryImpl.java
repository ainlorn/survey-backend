package com.midgetspinner31.survey.db.dao.impl;

import com.midgetspinner31.survey.db.dao.SurveyQueryRepository;
import com.midgetspinner31.survey.db.entity.Survey;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SurveyQueryRepositoryImpl extends AbstractQueryRepository<Survey> implements SurveyQueryRepository {

    protected SurveyQueryRepositoryImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public Page<Survey> findSurveyByTopics(List<String> topics,
                                           PageRequest pageRequest, AdditionalRespondentDetails details) {

        Query query = new Query().with(pageRequest);

        query.addCriteria(Criteria.where("attemptsLeft").gte(1));

        if (topics != null && !topics.isEmpty()) {
            query.addCriteria(Criteria.where("survey_topics").in(topics));
        }

        return getPage(query, pageRequest);
    }
}

