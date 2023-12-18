package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.SurveyRepository;
import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.db.entity.Question;
import com.midgetspinner31.survey.db.entity.Survey;
import com.midgetspinner31.survey.db.entity.User;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import com.midgetspinner31.survey.dto.SurveyDraftInfo;
import com.midgetspinner31.survey.dto.SurveyInfo;
import com.midgetspinner31.survey.dto.SurveyShortInfo;
import com.midgetspinner31.survey.enumerable.AccountType;
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

import java.time.*;
import java.time.temporal.ChronoUnit;
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
    @PreAuthorize("@surveyCreatorService.isSurveyCreator()")
    public SurveyInfo saveSurvey(SurveyRequest surveyRequest) {
        User user = userRepository.getCurrentUser();
        SurveyInfo surveyInfo = surveyFactory.createSurveyInfoFrom(user.getId(), surveyRequest);
        List<Question> questions = surveyFactory.createQuestionsFrom(surveyInfo.getQuestions());
        Survey survey = surveyFactory.createSurveyFrom(surveyInfo, questions);
        survey = surveyRepository.save(survey);
        return surveyFactory.createSurveyInfoFrom(survey);
    }

    @Override
    public SurveyInfo saveSurvey(SurveyDraftInfo surveyDraftInfo) {
        Survey survey = surveyFactory.createSurveyFrom(surveyDraftInfo);
        surveyRepository.save(survey);
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

    @Override
    public List<SurveyShortInfo> getSurveyShortList() {
        return surveyRepository.findAll().stream()
                .map(surveyFactory::createSurveyShortInfoFrom)
                .toList();
    }

    public Page<SurveyShortInfo> getSurveyPage(Integer page, Integer size, List<String> topics) {
        if (topics == null) {
            return surveyRepository.findAll(PageRequest.of(page, size))
                    .map(surveyFactory::createSurveyShortInfoFrom);
        }

        return surveyRepository.findBySurveyTopicsIn(topics, PageRequest.of(page, size))
                .map(surveyFactory::createSurveyShortInfoFrom);
    }

    @Override
    @PreAuthorize("@surveyCreatorService.isSurveyCreator()")
    public List<SurveyInfo> getSurveysCreatedByCurrentUser() {
        return surveyRepository
                .findAllByCreatorId(userRepository.getCurrentUser().getId())
                .stream()
                .map(surveyFactory::createSurveyInfoFrom)
                .toList();
    }

    @Override
    public Boolean currentUserMatchesRestrictions(String surveyId) {
        var user = userRepository.getCurrentUser();
        if (user == null || user.getAccountType() != AccountType.respondent)
            return null;
        var survey = surveyRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);
        var restrictions = survey.getRespondentRestrictions();
        var details = (AdditionalRespondentDetails) user.getAdditionalDetails();

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
