package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import com.midgetspinner31.survey.enumerable.AccountType;
import com.midgetspinner31.survey.service.RespondentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("respondentService")
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RespondentServiceImpl implements RespondentService {
    UserRepository userRepository;

    @Override
    @PreAuthorize("isAuthenticated()")
    public boolean isRespondent() {
        return userRepository.getCurrentUser().getAccountType() == AccountType.respondent;
    }

    @Override
    @PreAuthorize("@respondentService.isRespondent()")
    public AdditionalRespondentDetails getRespondentDetails() {
        return (AdditionalRespondentDetails) userRepository.getCurrentUser().getAdditionalDetails();
    }
}
