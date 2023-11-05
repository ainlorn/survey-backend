package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.SurveyRepository;
import com.midgetspinner31.survey.db.entity.Question;
import com.midgetspinner31.survey.db.entity.Survey;
import com.midgetspinner31.survey.dto.SurveyInfo;
import com.midgetspinner31.survey.factory.SurveyFactory;
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
    SurveyFactory surveyFactory;

    @Override
    public SurveyInfo getSurvey(String id) {
        // TODO: exception
        return surveyRepository.findById(id).orElseThrow().toSurveyInfo();
    }

    @Override
    public SurveyInfo saveSurvey(SurveyInfo surveyInfo) {
        List<Question> questions = surveyFactory.createQuestionsFrom(surveyInfo.getQuestions());
        Survey survey = surveyFactory.createSurveyFrom(surveyInfo, questions);

        return surveyRepository.save(survey).toSurveyInfo();
    }

    @Override
    public String deleteSurvey(String id) {
        return "Ok";
    }
}
