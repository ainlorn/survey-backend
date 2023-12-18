package com.midgetspinner31.survey.factory;

import com.midgetspinner31.survey.db.entity.Question;
import com.midgetspinner31.survey.db.entity.Restrictions;
import com.midgetspinner31.survey.db.entity.SurveyDraft;
import com.midgetspinner31.survey.dto.QuestionInfo;
import com.midgetspinner31.survey.dto.RestrictionsInfo;
import com.midgetspinner31.survey.dto.SurveyDraftInfo;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SurveyDraftFactory {
    public SurveyDraft createSurveyDraftFrom(SurveyDraftInfo surveyDraftInfo) {
        return SurveyDraft.builder()
                .name(surveyDraftInfo.getName())
                .description(surveyDraftInfo.getDescription())
                .surveyTopics(surveyDraftInfo.getSurveyTopics())
                .creatorId(surveyDraftInfo.getCreatorId())
                .creationDate(surveyDraftInfo.getCreationDate())
                .questions(createQuestionsFrom(surveyDraftInfo.getQuestions()))
                .respondentRestrictions(surveyDraftInfo.getRespondentRestrictions())
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

    public SurveyDraftInfo createSurveyDraftInfoFrom(String creatorId, SurveyRequest surveyRequest) {
        return new SurveyDraftInfo(
                null,
                surveyRequest.getName(),
                surveyRequest.getDescription(),
                surveyRequest.getSurveyTopics(),
                creatorId,
                new Date(),
                surveyRequest.getQuestions(),
                surveyRequest.getRespondentRestrictions()
        );
    }

    public SurveyDraftInfo createSurveyDraftInfoFrom(SurveyDraft surveyDraft) {
        return new SurveyDraftInfo(
                surveyDraft.getId(),
                surveyDraft.getName(),
                surveyDraft.getDescription(),
                surveyDraft.getSurveyTopics(),
                surveyDraft.getCreatorId(),
                new Date(),
                surveyDraft.getQuestions().stream().map(Question::toQuestionInfo).toList(),
                surveyDraft.getRespondentRestrictions()
        );
    }
}
