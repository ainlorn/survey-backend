package com.midgetspinner31.survey.web.advice;

import com.midgetspinner31.survey.web.annotation.ApiAdvice;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ApiAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SurveyAnswerAdvice extends DefaultAdvice {
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<String> noSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
