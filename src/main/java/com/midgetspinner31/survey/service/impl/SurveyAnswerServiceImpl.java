package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.FileExporter;
import com.midgetspinner31.survey.db.dao.SurveyAnswerRepository;
import com.midgetspinner31.survey.db.dao.SurveyRepository;
import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.db.entity.SurveyAnswer;
import com.midgetspinner31.survey.db.entity.User;
import com.midgetspinner31.survey.db.entity.answers.*;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import com.midgetspinner31.survey.dto.*;
import com.midgetspinner31.survey.exception.*;
import com.midgetspinner31.survey.factory.SurveyAnswerFactory;
import com.midgetspinner31.survey.service.*;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SurveyAnswerServiceImpl implements SurveyAnswerService {
    UserRepository userRepository;

    SurveyAnswerFactory surveyAnswerFactory;
    SurveyRepository surveyRepository;
    SurveyAnswerRepository surveyAnswerRepository;
    SurveyService surveyService;
    FileExporter fileExporter;
    RespondentService respondentService;
    WalletService walletService;
    SurveyRewardService surveyRewardService;

    @Override
    @PreAuthorize("@respondentService.isRespondent()")
    public SurveyAnswerInfo saveSurveyAnswer(String surveyId, SurveyAnswerRequest surveyAnswerRequest) {
        User user = userRepository.getCurrentUser();
        SurveyInfo surveyInfo = surveyService.getSurvey(surveyId);

        if (!respondentService.currentUserMatchesRestrictions(surveyInfo.getRespondentRestrictions()))
            throw new RespondentRestrictionsNotMatchedException();

        if (surveyInfo.getAttemptsLeft() < 1) {
            throw new SurveyNotFoundException();
        }
        walletService.makeCreditPayment(
                user.getId(),
                surveyInfo.getCreatorId(),
                surveyRewardService.getSurveyAnswerPublishPrice(),
                String.format("Начисление средств за прохождение опроса '%s'", surveyInfo.getName())
        );

        SurveyAnswerInfo surveyAnswerInfo =
                surveyAnswerFactory.createSurveyAnswerInfoFrom(user.getId(), surveyInfo, surveyAnswerRequest);

        List<QuestionAnswer> questionAnswers = surveyAnswerInfo.getAnswers();
        List<QuestionInfo> questions = surveyInfo.getQuestions();
        validateAnswers(questions, questionAnswers);

        SurveyAnswer surveyAnswer = surveyAnswerFactory.createSurveyAnswerFrom(surveyAnswerInfo);
        surveyAnswer = surveyAnswerRepository.save(surveyAnswer);

        surveyService.subtractSurveyAttempt(surveyId);

        return surveyAnswerFactory.createSurveyAnswerInfoFrom(surveyAnswer);
    }

    private void validateAnswers(List<QuestionInfo> questions, List<QuestionAnswer> answers) {
        if (questions.size() != answers.size())
            throw new SurveyAnswerValidationException(
                    "Number of answers (%d) does not match number of questions (%d)!"
                            .formatted(answers.size(), questions.size()));

        for (int i = 0; i < answers.size(); i++) {
            QuestionAnswer answer = answers.get(i);
            QuestionInfo question = questions.get(i);
            RestrictionsInfo restrictions = question.getRestrictionsInfo();

            if (answer == null) {
                if (question.getRequired())
                    throw new SurveyAnswerValidationException(
                            "Missing answer to a required question #%d".formatted(i));
                continue;
            }

            switch (question.getAnswerType()) {
                case text:
                    String text = ((TextAnswer) answer).getText();
                    if (restrictions.getMaxLength() != null && text.length() > restrictions.getMaxLength())
                        throw new SurveyAnswerValidationException(
                                "Text answer length (%d) is longer than max length (%d)"
                                        .formatted(text.length(), restrictions.getMaxLength()));
                    break;
                case slider:
                    int number = ((SliderAnswer) answer).getValue();
                    if (number > restrictions.getMax() || number < restrictions.getMin())
                        throw new SurveyAnswerValidationException(
                                "Slider answer value (%d) outside of allowed bounds (%d-%d)"
                                        .formatted(number, restrictions.getMin(), restrictions.getMax()));
                    break;
                case single_choice:
                    int choice = ((SingleChoiceAnswer) answer).getChoice();
                    validateChoice(choice, restrictions);
                    break;
                case multiple_choice:
                    List<Integer> choices = ((MultipleChoiceAnswer) answer).getChoices();
                    if (choices.size() != new HashSet<>(choices).size())
                        throw new SurveyAnswerValidationException(
                                "Multiple choice answer has the same option picked multiple times!");
                    choices.forEach((x) -> validateChoice(x, restrictions));
                    break;
            }
        }
    }

    private void validateChoice(int choice, RestrictionsInfo restrictions) {
        if (choice < 0 || choice >= restrictions.getChoices().size())
            throw new SurveyAnswerValidationException(
                    "Choice answer number %d outside of allowed bounds (%d-%d)"
                            .formatted(choice, 0, restrictions.getChoices().size()));
    }

    @Override
    @PreAuthorize("@surveyCreatorService.isSurveyCreator()")
    public SurveyAnswerInfo getSurveyAnswer(String surveyId, String answerId) {
        SurveyAnswer answer = surveyAnswerRepository.findById(answerId)
                .orElseThrow(SurveyAnswerNotFoundException::new);
        if (!Objects.equals(answer.getSurveyId(), surveyId))
            throw new SurveyAnswerNotFoundException();

        return surveyAnswerFactory.createSurveyAnswerInfoFrom(answer);
    }

    @Override
    @PreAuthorize("@surveyCreatorService.isSurveyCreator()")
    public List<SurveyAnswerShortInfo> getSurveyAnswersBySurveyId(String surveyId) {
        return surveyAnswerRepository.findAllBySurveyId(surveyId).stream()
                .map(surveyAnswerFactory::createSurveyAnswerShortInfoFrom)
                .toList();
    }

    @Override
    @PreAuthorize("@surveyCreatorService.isSurveyCreator()")
    public List<SurveySingleAnswerInfo> getSurveySingleQuestionAnswers(String surveyId, int questionId) {
        var survey = surveyRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);
        if (questionId >= survey.getQuestions().size())
            throw new QuestionNotFoundException();

        return surveyAnswerRepository.findAllBySurveyId(surveyId).stream()
                .map((x) -> new SurveySingleAnswerInfo(
                        x.getRespondentId(),
                        (AdditionalRespondentDetails) userRepository.findById(x.getRespondentId())
                                .map(User::getAdditionalDetails)
                                .orElse(null),
                        x.getAnswers().get(questionId))).toList();
    }

    @Override
    @PreAuthorize("@surveyCreatorService.isSurveyCreator()")
    public void exportRespondentDetailsBySurveyIdToCsv(
            String surveyId,
            List<String> fields,
            HttpServletResponse httpServletResponse) throws IOException {


        List<String> fieldNames = Arrays
                .stream(AdditionalRespondentDetails.class.getDeclaredFields())
                .map(Field::getName).toList();

        if (fields == null || fields.isEmpty()) {
            fields = fieldNames;
        } else {
            fields.forEach(field -> {
                if (!fieldNames.contains(field)) {
                    throw new IncorrectRespondentDetailsFieldException("Некорректное указано поле респондента: " + field);
                }
            });
        }

        List<SurveyAnswerShortInfo> shortInfos = getSurveyAnswersBySurveyId(surveyId);
        List<AdditionalRespondentDetails> respondentDetails = shortInfos.stream()
                .map(SurveyAnswerShortInfo::getRespondentDetails)
                .toList();

        fileExporter.exportRespondentDetailsToCsv(respondentDetails, fields.toArray(new String[0]), httpServletResponse, surveyId);

    }

    public void exportRespondentDetailsBySurveyIdToXlsx(String surveyId,
                                                        List<String> fields,
                                                        HttpServletResponse httpServletResponse) throws IOException {
        List<String> fieldNames = Arrays
                .stream(AdditionalRespondentDetails.class.getDeclaredFields())
                .map(Field::getName).toList();

        if (fields == null || fields.isEmpty()) {
            fields = fieldNames;
        } else {
            fields.forEach(field -> {
                if (!fieldNames.contains(field)) {
                    throw new IncorrectRespondentDetailsFieldException("Некорректное указано поле респондента: " + field);
                }
            });
        }

        List<SurveyAnswerShortInfo> shortInfos = getSurveyAnswersBySurveyId(surveyId);
        List<AdditionalRespondentDetails> respondentDetails = shortInfos.stream()
                .map(SurveyAnswerShortInfo::getRespondentDetails)
                .toList();

        fileExporter.exportRespondentDetailsToXlsx(respondentDetails, fields.toArray(new String[0]), httpServletResponse, surveyId);

    }
}
