package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.Interview;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface InterviewQueryRepository {

    Page<Interview> findInterviewsByInterviewTopics(List<String> interviewTopics,
                                                    PageRequest pageRequest, AdditionalRespondentDetails details);
}
