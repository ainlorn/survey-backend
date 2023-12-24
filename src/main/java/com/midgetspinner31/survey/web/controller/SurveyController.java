package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.service.SurveyAnswerService;
import com.midgetspinner31.survey.service.SurveyService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import com.midgetspinner31.survey.web.response.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        var response = new SurveyResponse(surveyService.getSurvey(surveyId));
        response.setUserMeetsRestrictions(surveyService.currentUserMatchesRestrictions(response.getSurveyInfo().getId()));
        return response;
    }

    /**
     * Получить список доступных опросов
     */
    @GetMapping("/surveys")
    public SurveyShortListResponse getSurveyList() {
        return new SurveyShortListResponse(surveyService.getSurveyShortList());
    }

    /**
     * Получить список опросов, созданных текущим пользователем
     */
    @GetMapping("/me/surveys")
    public SurveyListResponse getMySurveys() {
        return new SurveyListResponse(surveyService.getSurveysCreatedByCurrentUser());
    }

    /**
     * Получить список опросов с пагинацией
     *
     * @param offset страница
     * @param limit  количество элементов на странице
     * @param topics (опционально) темы опроса
     */
    @GetMapping("/surveys/page")
    public SurveyShortPageResponse getSurveyListPage(
            @RequestParam @Min(0) Integer offset,
            @RequestParam @Min(1) @Max(100) Integer limit,
            @RequestParam(name = "topic", required = false) List<String> topics) {

        return new SurveyShortPageResponse(surveyService.getSurveyPage(offset, limit, topics));
    }

    /**
     * Удалить опрос
     *
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
     *
     * @param surveyId id опроса
     */
    @GetMapping("/survey/{surveyId}/answers")
    public SurveyAnswerListResponse getSurveyAnswers(@PathVariable String surveyId) {
        return new SurveyAnswerListResponse(surveyAnswerService.getSurveyAnswersBySurveyId(surveyId));
    }

    /**
     * Экспорт csv с информацией о респондентах
     *
     * @param surveyId            id опроса
     * @param fields              поля по которым нужно делать выборку: <br>
     *                            firstName, lastName, birthDate, gender, region, familyStatus, educationStatus,  income;
     * @param httpServletResponse
     * @return csv файл
     * @throws IOException
     */
    @GetMapping("/surveys/{surveyId}/csv")
    public void getRespondentInfoCSVForSurvey(
            @PathVariable String surveyId,
            @RequestParam(name = "field", required = false) List<String> fields,
            HttpServletResponse httpServletResponse) throws IOException {

        surveyAnswerService.exportRespondentDetailsBySurveyIdToCsv(surveyId, fields, httpServletResponse);
    }

    /**
     * Получить список ответов на конкретный вопрос
     *
     * @param surveyId   id опроса
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
