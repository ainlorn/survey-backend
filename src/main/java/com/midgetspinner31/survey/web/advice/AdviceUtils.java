package com.midgetspinner31.survey.web.advice;

import com.midgetspinner31.survey.constant.StatusCode;
import com.midgetspinner31.survey.web.response.ErrorResponse;
import org.springframework.http.ResponseEntity;

public class AdviceUtils {
    public static ResponseEntity<ErrorResponse> createResponse(StatusCode s) {
        return ResponseEntity.status(s.getHttpCode()).body(new ErrorResponse(s));
    }
}
