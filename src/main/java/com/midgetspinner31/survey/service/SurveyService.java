package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.SurveyInfo;

public interface SurveyService {
    SurveyInfo getSurvey(String id);
    SurveyInfo saveSurvey(SurveyInfo surveyInfo);
    String deleteSurvey(String id);
}
