package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.SurveyAnswerRepository;
import com.midgetspinner31.survey.db.entity.SurveyAnswer;
import com.midgetspinner31.survey.db.entity.answers.Answer;
import com.midgetspinner31.survey.db.entity.answers.TextAnswer;
import com.midgetspinner31.survey.dto.*;
import com.midgetspinner31.survey.factory.SurveyAnswerFactory;
import com.midgetspinner31.survey.service.SurveyAnswerService;
import com.midgetspinner31.survey.service.SurveyService;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.SizeLimitExceededException;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

    SurveyAnswerFactory surveyAnswerFactory;
    SurveyAnswerRepository surveyAnswerRepository;

    SurveyService surveyService;

    @Override
    public SurveyAnswerInfo saveSurveyAnswer(SurveyAnswerRequest surveyAnswerRequest) throws SizeLimitExceededException {
        //TODO: Вынести валидацию в отдельный метод

        //NoSuchElementException
        SurveyInfo surveyInfo = surveyService.getSurvey(surveyAnswerRequest.getSurveyId());
        List<QuestionAnswerInfo> questionAnswerInfoList = surveyAnswerRequest.getAnswers();
        List<QuestionInfo> questionInfoList = surveyInfo.getQuestions();
        //TODO: Баг с геттером


        if (questionInfoList.size() != questionAnswerInfoList.size()) {
            // TODO: Custom Exception
            throw new SizeLimitExceededException();
        }
        for (int i = 0; i < questionAnswerInfoList.size(); i++) {
            Answer answer = questionAnswerInfoList.get(i).getAnswer();
            RestrictionsInfo restrictionsInfo = questionInfoList.get(i).getRestrictionsInfo();
            System.out.println(answer.getAnswerType().length());
            String type = answer.getAnswerType();
            if (type.equals("text")) {
                TextAnswer txt = (TextAnswer) answer;
                System.out.println(txt);
                if (txt.getText().length() > restrictionsInfo.getMaxLength()) {
                    //TODO: Custom Exception
                    throw new SizeLimitExceededException();
                }
            }
        }

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
