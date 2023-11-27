package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.SurveyInfo;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SurveyService {
    SurveyInfo getSurvey(String id);

    SurveyInfo saveSurvey(SurveyRequest surveyRequest);

    String deleteSurvey(String id);

    List<SurveyInfo> getSurveyList();

    Page<SurveyInfo> getSurveyPage(Integer page, Integer size);
}
