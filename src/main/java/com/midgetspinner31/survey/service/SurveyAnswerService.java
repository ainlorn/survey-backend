package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.SurveyAnswerInfo;
import com.midgetspinner31.survey.dto.SurveyAnswerShortInfo;
import com.midgetspinner31.survey.dto.SurveySingleAnswerInfo;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface SurveyAnswerService {
    SurveyAnswerInfo saveSurveyAnswer(String surveyId, SurveyAnswerRequest surveyAnswerRequest);

    SurveyAnswerInfo getSurveyAnswer(String surveyId, String answerId);

    List<SurveyAnswerShortInfo> getSurveyAnswersBySurveyId(String surveyId);

    List<SurveySingleAnswerInfo> getSurveySingleQuestionAnswers(String surveyId, int questionId);

    void exportRespondentDetailsBySurveyIdToCsv(
            String surveyId,
            List<String> fields,
            HttpServletResponse httpServletResponse) throws IOException;

    void exportRespondentDetailsBySurveyIdToXlsx(
            String surveyId,
            List<String> fields,
            HttpServletResponse httpServletResponse) throws IOException;
}
