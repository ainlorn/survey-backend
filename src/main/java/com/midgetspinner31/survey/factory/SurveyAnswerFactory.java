package com.midgetspinner31.survey.factory;

import com.midgetspinner31.survey.db.entity.QuestionAnswer;
import com.midgetspinner31.survey.db.entity.SurveyAnswer;
import com.midgetspinner31.survey.dto.QuestionAnswerInfo;
import com.midgetspinner31.survey.dto.SurveyAnswerInfo;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SurveyAnswerFactory {
    public SurveyAnswer createSurveyAnswerFrom(SurveyAnswerInfo surveyAnswerInfo) {
        return SurveyAnswer.builder()
                .surveyId(surveyAnswerInfo.getSurveyId())
                .answeredAt(surveyAnswerInfo.getAnsweredAt())
                //TODO: брать id из сессии
                .respondentId(surveyAnswerInfo.getRespondentId())
                .pollingTime(surveyAnswerInfo.getPollingTime())
                .answers(createQuestionAnswersFrom(surveyAnswerInfo.getAnswers()))
                .build();
    }

    public QuestionAnswer createQuestionAnswerFrom(QuestionAnswerInfo questionAnswerInfo) {
        return QuestionAnswer.builder()
                .answer(questionAnswerInfo.getAnswer())
                .build();
    }

    public List<QuestionAnswer> createQuestionAnswersFrom(List<QuestionAnswerInfo> questionAnswerInfos) {
        return questionAnswerInfos.stream()
                .map(this::createQuestionAnswerFrom)
                .toList();
    }

    public SurveyAnswerInfo createSurveyAnswerInfoFrom(SurveyAnswer surveyAnswer) {
        return new SurveyAnswerInfo(
                surveyAnswer.getSurveyId(),
                surveyAnswer.getAnsweredAt(),
                surveyAnswer.getRespondentId(),
                surveyAnswer.getPollingTime(),
                createQuestionAnswerInfosFrom(surveyAnswer.getAnswers()));
    }

    public QuestionAnswerInfo createQuestionAnswerInfoFrom(QuestionAnswer questionAnswer) {
        return new QuestionAnswerInfo(questionAnswer.getAnswer());
    }

    public List<QuestionAnswerInfo> createQuestionAnswerInfosFrom(List<QuestionAnswer> questionAnswer) {
        return questionAnswer.stream()
                .map(this::createQuestionAnswerInfoFrom)
                .toList();
    }


    public SurveyAnswerInfo createSurveyAnswerInfoFrom(SurveyAnswerRequest surveyAnswerRequest) {
        return new SurveyAnswerInfo(
                surveyAnswerRequest.getSurveyId(),
                new Date(),
                surveyAnswerRequest.getRespondentId(),
                surveyAnswerRequest.getPollingTime(),
                surveyAnswerRequest.getAnswers());
    }

}
