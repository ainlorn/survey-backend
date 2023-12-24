package com.midgetspinner31.survey.web.response;

import com.midgetspinner31.survey.constant.StatusCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse extends BaseResponse {
    public ErrorResponse(StatusCode statusCode) {
        super.setStatus(statusCode.getCode());
        super.setMessage(statusCode.getMessage());
    }

    public ErrorResponse(StatusCode statusCode, String message) {
        super.setStatus(statusCode.getCode());
        super.setMessage(message);
    }
}
