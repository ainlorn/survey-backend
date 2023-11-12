package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.SurveyRepository;
import com.midgetspinner31.survey.db.entity.Question;
import com.midgetspinner31.survey.db.entity.Survey;
import com.midgetspinner31.survey.dto.SurveyInfo;
import com.midgetspinner31.survey.factory.SurveyFactory;
import com.midgetspinner31.survey.service.SurveyService;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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
        return surveyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Опрос не найден")).toSurveyInfo();
    }

    @Override
    public SurveyInfo saveSurvey(SurveyRequest surveyRequest) {
        SurveyInfo surveyInfo = surveyFactory.createSurveyInfoFrom(surveyRequest);
        List<Question> questions = surveyFactory.createQuestionsFrom(surveyInfo.getQuestions());
        Survey survey = surveyFactory.createSurveyFrom(surveyInfo, questions);
        surveyRepository.save(survey);
        return surveyInfo;
    }

    @Override
    public String deleteSurvey(String id) {
        return "Ok";
    }

    @Override
    public List<SurveyInfo> getSurveyList() {
        return surveyRepository.findAll().stream()
                .map(Survey::toSurveyInfo)
                .toList();
    }
}
