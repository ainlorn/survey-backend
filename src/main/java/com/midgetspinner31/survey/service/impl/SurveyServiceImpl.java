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
import com.midgetspinner31.survey.exception.LowBalanceException;
import com.midgetspinner31.survey.exception.SurveyNotFoundException;
import com.midgetspinner31.survey.factory.SurveyFactory;
import com.midgetspinner31.survey.service.SurveyService;
import com.midgetspinner31.survey.service.WalletService;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    UserRepository userRepository;
    SurveyRepository surveyRepository;
    SurveyFactory surveyFactory;
    WalletService walletService;

    // Stub
    private static final BigDecimal SUBTRACT_AMOUNT = new BigDecimal(100);

    @Override
    public SurveyInfo getSurvey(String id) {
        return surveyFactory.createSurveyInfoFrom(
                surveyRepository.findById(id).orElseThrow(SurveyNotFoundException::new));
    }

    @Override
    @PreAuthorize("@surveyCreatorService.isSurveyCreator()")
    public SurveyInfo saveSurvey(SurveyRequest surveyRequest) {
        User user = userRepository.getCurrentUser();
        //TODO: Сравнивать с суммой которую закладывает создатель на количество прохождений
        if (!walletService.canSubtractMoneyFromUserWallet(user.getId(), SUBTRACT_AMOUNT)) {
            throw new LowBalanceException();
        }
        SurveyInfo surveyInfo = surveyFactory.createSurveyInfoFrom(user.getId(), surveyRequest);
        List<Question> questions = surveyFactory.createQuestionsFrom(surveyInfo.getQuestions());
        Survey survey = surveyFactory.createSurveyFrom(surveyInfo, questions);
        survey = surveyRepository.save(survey);

        walletService.subtractMoneyFromUserWallet(user.getId(), SUBTRACT_AMOUNT);
        return surveyFactory.createSurveyInfoFrom(survey);
    }

    @Override
    public SurveyInfo saveSurvey(SurveyDraftInfo surveyDraftInfo) {
        String userId = userRepository.getCurrentUser().getId();
        if (!walletService.canSubtractMoneyFromUserWallet(userId, SUBTRACT_AMOUNT)) {
            throw new LowBalanceException();
        }
        Survey survey = surveyFactory.createSurveyFrom(surveyDraftInfo);
        surveyRepository.save(survey);
        walletService.subtractMoneyFromUserWallet(userId, SUBTRACT_AMOUNT);
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

        AdditionalRespondentDetails details = (AdditionalRespondentDetails) userRepository.getCurrentUser()
                .getAdditionalDetails();

        return surveyRepository.findSurveyByTopics(topics, PageRequest.of(page, size), details).map(surveyFactory::createSurveyShortInfoFrom);
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
}
