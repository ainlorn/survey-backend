package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.Interview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface InterviewRepository extends MongoRepository<Interview, String> {
    Interview getById(String id);

    @Query("{ 'interviewTopics': {$in: ?0} }")
    Page<Interview> findAllByInterviewTopicsIn(List<String> interviewTopics, PageRequest pageRequest);

    List<Interview> findAllByCreatorId(String creatorId);
}
