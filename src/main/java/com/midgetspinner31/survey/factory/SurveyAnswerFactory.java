package com.midgetspinner31.survey.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midgetspinner31.survey.db.entity.SurveyAnswer;
import com.midgetspinner31.survey.db.entity.answers.QuestionAnswer;
import com.midgetspinner31.survey.dto.SurveyAnswerInfo;
import com.midgetspinner31.survey.dto.SurveyAnswerShortInfo;
import com.midgetspinner31.survey.dto.SurveyInfo;
import com.midgetspinner31.survey.exception.SurveyAnswerValidationException;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class SurveyAnswerFactory {
    private final ObjectMapper objectMapper;

    public SurveyAnswer createSurveyAnswerFrom(SurveyAnswerInfo surveyAnswerInfo) {
        return SurveyAnswer.builder()
                .surveyId(surveyAnswerInfo.getSurveyId())
                .answeredAt(surveyAnswerInfo.getAnsweredAt())
                .respondentId(surveyAnswerInfo.getRespondentId())
                .pollingTime(surveyAnswerInfo.getPollingTime())
                .answers(surveyAnswerInfo.getAnswers())
                .build();
    }

    public SurveyAnswerInfo createSurveyAnswerInfoFrom(SurveyAnswer surveyAnswer) {
        return new SurveyAnswerInfo(
                surveyAnswer.getSurveyId(),
                surveyAnswer.getId(),
                surveyAnswer.getAnsweredAt(),
                surveyAnswer.getRespondentId(),
                surveyAnswer.getPollingTime(),
                surveyAnswer.getAnswers());
    }

    public SurveyAnswerShortInfo createSurveyAnswerShortInfoFrom(SurveyAnswer surveyAnswer) {
        return new SurveyAnswerShortInfo(
                surveyAnswer.getSurveyId(),
                surveyAnswer.getId(),
                surveyAnswer.getAnsweredAt(),
                surveyAnswer.getRespondentId(),
                surveyAnswer.getPollingTime()
        );
    }

    public SurveyAnswerInfo createSurveyAnswerInfoFrom(String respondentId,
                                                       SurveyInfo surveyInfo,
                                                       SurveyAnswerRequest surveyAnswerRequest) {
        var questions = surveyInfo.getQuestions();
        var answerNodes = surveyAnswerRequest.getAnswers();
        var answers = new ArrayList<QuestionAnswer>();

        if (questions.size() != answerNodes.size())
            throw new SurveyAnswerValidationException(
                    "Number of answers (%d) does not match number of questions (%d)!"
                            .formatted(answerNodes.size(), questions.size()));

        try {
            for (var i = 0; i < questions.size(); i++)
                answers.add(objectMapper.treeToValue(
                                answerNodes.get(i),
                                questions.get(i).getAnswerType().getAnswerClass()));
        } catch (JsonProcessingException e) {
            throw new SurveyAnswerValidationException(e);
        }

        return new SurveyAnswerInfo(
                surveyInfo.getId(),
                null,
                new Date(),
                respondentId,
                surveyAnswerRequest.getPollingTime(),
                answers);
    }
}
