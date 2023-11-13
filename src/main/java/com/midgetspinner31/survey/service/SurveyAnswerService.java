package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.SurveyAnswerInfo;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;

import javax.naming.SizeLimitExceededException;
import java.util.List;

public interface SurveyAnswerService {
    SurveyAnswerInfo saveSurveyAnswer(String surveyId, SurveyAnswerRequest surveyAnswerRequest);

    SurveyAnswerInfo getSurveyAnswer(String surveyId, String answerId);

    List<SurveyAnswerInfo> getSurveyAnswersBySurveyId(String surveyId);
}
