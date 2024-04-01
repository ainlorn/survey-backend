package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.SurveyDraftInfo;
import com.midgetspinner31.survey.dto.SurveyInfo;
import com.midgetspinner31.survey.dto.SurveyShortInfo;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SurveyService {
    SurveyInfo getSurvey(String id);

    SurveyInfo saveSurvey(SurveyRequest surveyRequest);

    SurveyInfo saveSurvey(SurveyDraftInfo surveyDraftInfo);

    String deleteSurvey(String id);

    List<SurveyInfo> getSurveyList();

    List<SurveyShortInfo> getSurveyShortList();

    Page<SurveyShortInfo> getSurveyPage(Integer page, Integer size, List<String> topics);

    List<SurveyInfo> getSurveysCreatedByCurrentUser();
}
