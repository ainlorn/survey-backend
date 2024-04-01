package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.db.entity.RespondentRestrictions;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import com.midgetspinner31.survey.enumerable.AccountType;
import com.midgetspinner31.survey.service.RespondentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

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

    @Override
    public boolean currentUserMatchesRestrictions(RespondentRestrictions restrictions) {

        AdditionalRespondentDetails details = getRespondentDetails();

        if (restrictions == null)
            return true;

        Integer age = null;
        if (details.getBirthDate() != null) {
            age = (int) ChronoUnit.YEARS.between(
                    details.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                    LocalDateTime.now()
            );
        }

        if (restrictions.getMinAge() != null
                && (age == null || age < restrictions.getMinAge()))
            return false;

        if (restrictions.getMaxAge() != null
                && (age == null || age > restrictions.getMaxAge()))
            return false;

        if (restrictions.getAllowedGenders() != null
                && (details.getGender() == null || !restrictions.getAllowedGenders().contains(details.getGender())))
            return false;

        if (restrictions.getAllowedRegions() != null
                && (details.getRegion() == null || !restrictions.getAllowedRegions().contains(details.getRegion())))
            return false;

        if (restrictions.getAllowedEducation() != null
                && (details.getEducationStatus() == null || !restrictions.getAllowedEducation().contains(details.getEducationStatus())))
            return false;

        if (restrictions.getAllowedFamilyStatus() != null
                && (details.getFamilyStatus() == null || !restrictions.getAllowedFamilyStatus().contains(details.getFamilyStatus())))
            return false;

        if (restrictions.getMinIncome() != null
                && (details.getIncome() == null || details.getIncome() < restrictions.getMinIncome()))
            return false;

        if (restrictions.getMaxIncome() != null
                && (details.getIncome() == null || details.getIncome() > restrictions.getMaxIncome()))
            return false;
        return true;
    }
}
