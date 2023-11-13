package com.midgetspinner31.survey.factory;

import com.midgetspinner31.survey.db.entity.Question;
import com.midgetspinner31.survey.db.entity.Restrictions;
import com.midgetspinner31.survey.db.entity.Survey;
import com.midgetspinner31.survey.dto.QuestionInfo;
import com.midgetspinner31.survey.dto.RestrictionsInfo;
import com.midgetspinner31.survey.dto.SurveyInfo;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SurveyFactory {
    public Survey createSurveyFrom(SurveyInfo surveyInfo, List<Question> questions) {
        return Survey.builder()
                .name(surveyInfo.getName())
                .description(surveyInfo.getDescription())
                .surveyTopics(surveyInfo.getSurveyTopics())
                .creatorId(surveyInfo.getCreatorId())
                .creationDate(surveyInfo.getCreationDate())
                .questions(questions)
                .build();
    }
    public Question createQuestionFrom(QuestionInfo questionInfo, Restrictions restrictions) {
        return Question.builder()
                .text(questionInfo.getText())
                .required(questionInfo.getRequired())
                .answerType(questionInfo.getAnswerType())
                .restrictions(restrictions)
                .build();
    }

    public List<Question> createQuestionsFrom(List<QuestionInfo> questionInfos) {
        return questionInfos.stream()
                .map(questionInfo -> {
                    Restrictions restrictions = createRestrictionsFrom(questionInfo.getRestrictionsInfo());
                    return createQuestionFrom(questionInfo, restrictions);
                }).toList();
    }

    public Restrictions createRestrictionsFrom(RestrictionsInfo restrictionsInfo) {
        return Restrictions.builder()
                .min(restrictionsInfo.getMin())
                .max(restrictionsInfo.getMax())
                .maxLength(restrictionsInfo.getMaxLength())
                .choices(restrictionsInfo.getChoices())
                .build();
    }

    public SurveyInfo createSurveyInfoFrom(String creatorId, SurveyRequest surveyRequest) {
        return new SurveyInfo(
                null,
                surveyRequest.getName(),
                surveyRequest.getDescription(),
                surveyRequest.getSurveyTopics(),
                creatorId,
                new Date(),
                surveyRequest.getQuestions()
        );
    }

    public SurveyInfo createSurveyInfoFrom(Survey survey) {
        return new SurveyInfo(
                survey.getId(),
                survey.getName(),
                survey.getDescription(),
                survey.getSurveyTopics(),
                survey.getCreatorId(),
                survey.getCreationDate(),
                survey.getQuestions().stream().map(Question::toQuestionInfo).toList()
        );
    }
}
