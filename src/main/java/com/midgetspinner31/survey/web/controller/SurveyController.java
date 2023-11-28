package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.service.SurveyAnswerService;
import com.midgetspinner31.survey.service.SurveyService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import com.midgetspinner31.survey.web.response.SurveyAnswerResponse;
import com.midgetspinner31.survey.web.response.SurveyResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<SurveyResponse> getSurveyList() {
        return surveyService.getSurveyList().stream()
                .map(SurveyResponse::new)
                .toList();
    }

    //TODO: Заменить getSurveyList этим методом (переопределить)
    @GetMapping("/surveys/page")
    public Page<SurveyResponse> getSurveyListPage(
            @RequestParam @Min(0) Integer offset,
            @RequestParam @Min(1) @Max(100) Integer limit,
            @RequestParam(name = "topic", required = false) List<String> topics) {

        System.out.println(topics);

        return surveyService.getSurveyPage(offset, limit, topics).map(SurveyResponse::new);
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
     * Получить информацию об ответе на опрос
     * @param surveyId id опроса
     * @param answerId id ответа
     */
    @GetMapping("/surveys/{surveyId}/answers/{answerId}")
    public SurveyAnswerResponse getSurveyAnswer(@PathVariable String surveyId, @PathVariable String answerId) {
        return new SurveyAnswerResponse(surveyAnswerService.getSurveyAnswer(surveyId, answerId));
    }
}
