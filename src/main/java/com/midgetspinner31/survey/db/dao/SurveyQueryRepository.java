package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.Survey;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface SurveyQueryRepository {

    Page<Survey> findSurveyByTopics(List<String> topics,
                                    PageRequest pageRequest, AdditionalRespondentDetails details);
}
