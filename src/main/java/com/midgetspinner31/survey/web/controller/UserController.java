package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.service.UserService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.request.UpdateCreatorDetailsRequest;
import com.midgetspinner31.survey.web.request.UpdateRespondentDetailsRequest;
import com.midgetspinner31.survey.web.response.UserInfoResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    /**
     * Обновить информацию о текущем пользователе (для респондентов)
     */
    @PatchMapping("/me/respondent_details")
    public UserInfoResponse updateRespondentDetails(@Valid @RequestBody UpdateRespondentDetailsRequest request) {
        return new UserInfoResponse(userService.updateCurrentUserAdditionalDetails(request.toAdditionalUserDetails()));
    }

    /**
     * Обновить информацию о текущем пользователе (для компаний)
     */
    @PatchMapping("/me/creator_details")
    public UserInfoResponse updateCreatorDetails(@Valid @RequestBody UpdateCreatorDetailsRequest request) {
        return new UserInfoResponse(userService.updateCurrentUserAdditionalDetails(request.toAdditionalUserDetails()));
    }
}
