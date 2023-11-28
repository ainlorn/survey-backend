package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.SurveyRepository;
import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.db.entity.Question;
import com.midgetspinner31.survey.db.entity.Survey;
import com.midgetspinner31.survey.db.entity.User;
import com.midgetspinner31.survey.dto.SurveyInfo;
import com.midgetspinner31.survey.exception.SurveyNotFoundException;
import com.midgetspinner31.survey.factory.SurveyFactory;
import com.midgetspinner31.survey.service.SurveyService;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    UserRepository userRepository;
    SurveyRepository surveyRepository;
    SurveyFactory surveyFactory;

    @Override
    public SurveyInfo getSurvey(String id) {
        return surveyFactory.createSurveyInfoFrom(
                surveyRepository.findById(id).orElseThrow(SurveyNotFoundException::new));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public SurveyInfo saveSurvey(SurveyRequest surveyRequest) {
        User user = userRepository.getCurrentUser();
        SurveyInfo surveyInfo = surveyFactory.createSurveyInfoFrom(user.getId(), surveyRequest);
        List<Question> questions = surveyFactory.createQuestionsFrom(surveyInfo.getQuestions());
        Survey survey = surveyFactory.createSurveyFrom(surveyInfo, questions);
        survey = surveyRepository.save(survey);
        return surveyFactory.createSurveyInfoFrom(survey);
    }

    @Override
    public String deleteSurvey(String id) {
        surveyRepository.deleteById(id);
        return "Ok";
    }

    @Override
    public List<SurveyInfo> getSurveyList() {
        return surveyRepository.findAll().stream()
                .map(surveyFactory::createSurveyInfoFrom)
                .toList();
    }

    public Page<SurveyInfo> getSurveyPage(Integer page, Integer size, List<String> topics) {
        if (topics == null) {
            return surveyRepository.findAll(PageRequest.of(page, size))
                    .map(surveyFactory::createSurveyInfoFrom);
        }

        return surveyRepository.findBySurveyTopicsIn(topics, PageRequest.of(page, size))
                .map(surveyFactory::createSurveyInfoFrom);
    }
}
