package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InterviewRepository extends MongoRepository<Interview, String>, InterviewQueryRepostiory {
    Interview getById(String id);

    List<Interview> findAllByCreatorId(String creatorId);
}
