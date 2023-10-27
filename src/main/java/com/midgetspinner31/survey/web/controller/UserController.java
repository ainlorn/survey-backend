package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.service.UserService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.response.UserInfoResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;

@SurveyApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    /**
     * Получить информацию о текущем пользователе
     */
    @GetMapping("/me")
    public UserInfoResponse getCurrentUserInfo() {
        return new UserInfoResponse(userService.getCurrentUserInfo());
    }
}
