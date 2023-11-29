package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.service.SurveyAnswerService;
import com.midgetspinner31.survey.service.SurveyService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import com.midgetspinner31.survey.web.response.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.SizeLimitExceededException;
import java.util.List;

@SurveyApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SurveyController {
    SurveyService surveyService;
    SurveyAnswerService surveyAnswerService;

    /**
     * Cохранить опрос
     */
    @PostMapping("/surveys")
    public SurveyResponse saveSurvey(@RequestBody SurveyRequest surveyRequest) {
        return new SurveyResponse(surveyService.saveSurvey(surveyRequest));
    }

    /**
     * Получить информацию об опросе
     * @param surveyId id опроса
     */
    @GetMapping("/surveys/{surveyId}")
    public SurveyResponse getSurvey(@PathVariable String surveyId) {
        return new SurveyResponse(surveyService.getSurvey(surveyId));
    }

    /**
     * Получить список доступных опросов
     */
    @GetMapping("/surveys")
    public SurveyListResponse getSurveyList() {
        return new SurveyListResponse(surveyService.getSurveyList());
    }

    /**
     * Получить список опросов, созданных текущим пользователем
     */
    @GetMapping("/me/surveys")
    public SurveyListResponse getMySurveys() {
        return new SurveyListResponse(surveyService.getSurveysCreatedByCurrentUser());
    }

    /**
     * Удалить опрос
     * @param surveyId id опроса
     */
    @DeleteMapping("/surveys/{surveyId}")
    public ResponseEntity<String> deleteSurvey(@PathVariable String surveyId) {
        return ResponseEntity.ok(surveyService.deleteSurvey(surveyId));
    }

    /**
     * Отправить ответ на опрос
     * @param surveyId id опроса
     */
    @PostMapping("/surveys/{surveyId}/answers")
    public SurveyAnswerResponse saveSurveyAnswer(@PathVariable String surveyId,
                                                 @RequestBody @Valid SurveyAnswerRequest surveyAnswerRequest) {
        return new SurveyAnswerResponse(surveyAnswerService.saveSurveyAnswer(surveyId, surveyAnswerRequest));
    }

    /**
     * Получить список ответов на опрос
     * @param surveyId id опроса
     */
    @GetMapping("/survey/{surveyId}/answers")
    public SurveyAnswerListResponse getSurveyAnswers(@PathVariable String surveyId) {
        return new SurveyAnswerListResponse(surveyAnswerService.getSurveyAnswersBySurveyId(surveyId));
    }

    /**
     * Получить список ответов на конкретный вопрос
     * @param surveyId id опроса
     * @param questionId индекс вопроса
     */
    @GetMapping("/survey/{surveyId}/single_answers/{questionId}")
    public SurveySingleAnswerListResponse getSurveySingleQuestionAnswers(@PathVariable String surveyId,
                                                                         @PathVariable int questionId) {
        return new SurveySingleAnswerListResponse(
                surveyAnswerService.getSurveySingleQuestionAnswers(surveyId, questionId));
    }

    /**
     * Получить информацию об ответе на опрос
     * @param surveyId id опроса
     * @param answerId id ответа
     */
    @GetMapping("/surveys/{surveyId}/answers/{answerId}")
    public SurveyAnswerResponse getSurveyAnswer(@PathVariable String surveyId, @PathVariable String answerId) {
        return new SurveyAnswerResponse(surveyAnswerService.getSurveyAnswer(surveyId, answerId));
    }
}
