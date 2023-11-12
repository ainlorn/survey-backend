package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.service.SurveyAnswerService;
import com.midgetspinner31.survey.service.SurveyService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
import com.midgetspinner31.survey.web.response.SurveyAnswerResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.SizeLimitExceededException;

@SurveyApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SurveyAnswerController {
    SurveyAnswerService surveyAnswerService;

    SurveyService surveyService;

    @PostMapping("/answer")
    public SurveyAnswerResponse saveSurveyAnswer(@RequestBody @Valid SurveyAnswerRequest surveyAnswerRequest) throws SizeLimitExceededException {

        return new SurveyAnswerResponse(surveyAnswerService.saveSurveyAnswer(surveyAnswerRequest));
    }

    @GetMapping("/answer")
    public SurveyAnswerResponse getSurveyAnswer(@RequestParam String id) {
        return new SurveyAnswerResponse(surveyAnswerService.getSurveyAnswer(id));
    }
}
