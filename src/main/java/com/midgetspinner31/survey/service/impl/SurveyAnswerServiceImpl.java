package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.SurveyAnswerRepository;
import com.midgetspinner31.survey.db.entity.SurveyAnswer;
import com.midgetspinner31.survey.dto.SurveyAnswerInfo;
import com.midgetspinner31.survey.factory.SurveyAnswerFactory;
import com.midgetspinner31.survey.service.SurveyAnswerService;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
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
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

    SurveyAnswerFactory surveyAnswerFactory;
    SurveyAnswerRepository surveyAnswerRepository;

    @Override
    public SurveyAnswerInfo saveSurveyAnswer(SurveyAnswerRequest surveyAnswerRequest) {
        //TODO: validation
        SurveyAnswerInfo surveyAnswerInfo = surveyAnswerFactory.createSurveyAnswerInfoFrom(surveyAnswerRequest);
        SurveyAnswer surveyAnswer = surveyAnswerFactory.createSurveyAnswerFrom(surveyAnswerInfo);
        surveyAnswerRepository.save(surveyAnswer);
        return surveyAnswerInfo;
    }

    @Override
    public SurveyAnswerInfo getSurveyAnswer(String id) {
        return surveyAnswerFactory.createSurveyAnswerInfoFrom(
                surveyAnswerRepository.findById(id).orElseThrow());
    }

    @Override
    public List<SurveyAnswerInfo> getSurveyAnswersBySurveyId(String surveyId) {
        return surveyAnswerRepository.findAllBySurveyId(surveyId).stream()
                .map(surveyAnswerFactory::createSurveyAnswerInfoFrom)
                .toList();
    }
}
