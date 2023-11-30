package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalSurveyCreatorDetails;
import com.midgetspinner31.survey.enumerable.AccountType;
import com.midgetspinner31.survey.service.SurveyCreatorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("surveyCreatorService")
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SurveyCreatorServiceImpl implements SurveyCreatorService {
    UserRepository userRepository;

    @Override
    @PreAuthorize("isAuthenticated()")
    public boolean isSurveyCreator() {
        return userRepository.getCurrentUser().getAccountType() == AccountType.survey_creator;
    }

    @Override
    @PreAuthorize("@surveyCreatorService.isSurveyCreator()")
    public AdditionalSurveyCreatorDetails getSurveyCreatorDetails() {
        return (AdditionalSurveyCreatorDetails) userRepository.getCurrentUser().getAdditionalDetails();
    }
}
