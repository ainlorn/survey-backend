package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.SurveyRepository;
import com.midgetspinner31.survey.db.entity.Question;
import com.midgetspinner31.survey.db.entity.Restrictions;
import com.midgetspinner31.survey.db.entity.Survey;
import com.midgetspinner31.survey.dto.RestrictionsInfo;
import com.midgetspinner31.survey.dto.SurveyInfo;
import com.midgetspinner31.survey.service.SurveyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    SurveyRepository surveyRepository;

    @Override
    public SurveyInfo getSurvey(String id) {
        // TODO: exception
        return surveyRepository.findById(id).orElseThrow().toSurveyInfo();
    }

    @Override
    public SurveyInfo saveSurvey(SurveyInfo surveyInfo) {
        //TODO: delegate survey building to factory
        List<Question> questions = surveyInfo.getQuestions().stream()
                .map(qInfo -> {
                    RestrictionsInfo restrictionsInfo = qInfo.getRestrictions();
                    Restrictions restrictions = Restrictions.builder()
                            .min(restrictionsInfo.getMin())
                            .max(restrictionsInfo.getMax())
                            .maxLength(restrictionsInfo.getMaxLength())
                            .choices(restrictionsInfo.getChoices())
                            .build();

                    return Question.builder()
                            .text(qInfo.getText())
                            .required(qInfo.getRequired())
                            .answerType(qInfo.getAnswerType())
                            .restrictions(restrictions)
                            .build();

                }).toList();
        Survey survey = Survey.builder()
                .name(surveyInfo.getName())
                .description(surveyInfo.getDescription())
                .surveyTopics(surveyInfo.getSurveyTopics())
                .creatorId(surveyInfo.getCreatorId())
                .creationDate(surveyInfo.getCreationDate())
                .questions(questions)
                .build();

        return surveyRepository.save(survey).toSurveyInfo();
    }

    @Override
    public String deleteSurvey(String id) {
        return "Ok";
    }
}
