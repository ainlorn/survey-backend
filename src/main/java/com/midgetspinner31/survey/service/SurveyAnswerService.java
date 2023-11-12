package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.SurveyAnswerInfo;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;

import java.util.List;

public interface SurveyAnswerService {
    SurveyAnswerInfo saveSurveyAnswer(SurveyAnswerRequest surveyAnswerRequest);

    SurveyAnswerInfo getSurveyAnswer(String id);

    List<SurveyAnswerInfo> getSurveyAnswersBySurveyId(String surveyId);
}
