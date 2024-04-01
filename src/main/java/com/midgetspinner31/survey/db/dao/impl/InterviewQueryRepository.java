package com.midgetspinner31.survey.db.dao.impl;

import com.midgetspinner31.survey.db.dao.InterviewQueryRepostiory;
import com.midgetspinner31.survey.db.entity.Interview;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import com.midgetspinner31.survey.db.utils.QueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InterviewQueryRepository extends AbstractQueryRepository<Interview> implements InterviewQueryRepostiory {

    protected InterviewQueryRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    /**
     * Тут вскрылись одни неприятные подробности, что у нас пагинация в монго идет в два запроса из коробки
     *
     * @link <a href="https://github.com/spring-projects/spring-data-mongodb/blob/3012bcd575d85ad7cd11e07962fc168d4bbc98b2/spring-data-mongodb/src/main/java/org/springframework/data/mongodb/repository/support/SimpleMongoRepository.java#L275-L285">магия репозиториев</a>
     */
    public Page<Interview> findInterviewsByInterviewTopics(List<String> interviewTopics,
                                                           PageRequest pageRequest, AdditionalRespondentDetails details) {
        Query query = new Query().with(pageRequest);

        query.addCriteria(QueryUtils.createRespondentRestrictionsCriteria(details));

        if (interviewTopics != null && !interviewTopics.isEmpty()) {
            query.addCriteria(Criteria.where("interviewTopics").in(interviewTopics));
        }

        return getPage(query, pageRequest);

    }

}

