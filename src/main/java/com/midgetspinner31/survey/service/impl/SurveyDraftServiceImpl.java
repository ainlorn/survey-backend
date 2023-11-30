package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.SurveyDraftRepository;
import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.db.entity.SurveyDraft;
import com.midgetspinner31.survey.db.entity.User;
import com.midgetspinner31.survey.dto.SurveyDraftInfo;
import com.midgetspinner31.survey.exception.SurveyNotFoundException;
import com.midgetspinner31.survey.factory.SurveyDraftFactory;
import com.midgetspinner31.survey.service.SurveyDraftService;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SurveyDraftServiceImpl implements SurveyDraftService {

    UserRepository userRepository;
    SurveyDraftRepository surveyDraftRepository;
    SurveyDraftFactory surveyDraftFactory;

    @Override
    public SurveyDraftInfo getSurveyDraft(String id) {
        return surveyDraftRepository.findById(id).orElseThrow(SurveyNotFoundException::new).toSurveyDraftInfo();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public SurveyDraftInfo saveSurveyDraft(SurveyRequest surveyRequest) {
        User user = userRepository.getCurrentUser();

        SurveyDraftInfo surveyDraftInfo = surveyDraftFactory.createSurveyDraftInfoFrom(user.getId(), surveyRequest);
        SurveyDraft surveyDraft = surveyDraftFactory.createSurveyDraftFrom(surveyDraftInfo);

        surveyDraftRepository.save(surveyDraft);
        return surveyDraftFactory.createSurveyDraftInfoFrom(surveyDraft);
    }

    @Override
    public String deleteSurveyDraft(String id) {
        surveyDraftRepository.deleteById(id);
        return "Ok";
    }
}
