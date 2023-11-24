package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.constant.StatusCode;
import com.midgetspinner31.survey.service.UserService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.request.SignInRequest;
import com.midgetspinner31.survey.web.request.RespondentSignUpRequest;
import com.midgetspinner31.survey.web.request.SurveyCreatorSignUpRequest;
import com.midgetspinner31.survey.web.response.BaseResponse;
import com.midgetspinner31.survey.web.response.EmptyResponse;
import com.midgetspinner31.survey.web.response.ErrorResponse;
import com.midgetspinner31.survey.web.response.UserInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SurveyApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountController {
    UserService userService;
    AuthenticationManager authenticationManager;
    RememberMeServices rememberMeServices;

    /**
     * Зарегистрировать аккаунт пользователя
     */
    @PostMapping("/register")
    public UserInfoResponse register(@Valid @RequestBody RespondentSignUpRequest request) {
        return new UserInfoResponse(userService.signUp(request.toUserSignUpInfo()));
    }

    /**
     * Зарегистрировать аккаунт создателя опросов
     */
    @PostMapping("/register_creator")
    public UserInfoResponse registerCreator(@Valid @RequestBody SurveyCreatorSignUpRequest request) {
        return new UserInfoResponse(userService.signUp(request.toUserSignUpInfo()));
    }

    /**
     * Войти в аккаунт пользователя
     */
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@Valid @RequestBody SignInRequest authRequest, HttpServletRequest request,
                                              HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        token.setDetails(new WebAuthenticationDetails(request));

        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
            if (auth != null && auth.isAuthenticated()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
                rememberMeServices.loginSuccess(request, response, auth);
            } else {
                throw new BadCredentialsException(null);
            }
        } catch (BadCredentialsException e) {
            SecurityContextHolder.getContext().setAuthentication(null);

            var status = StatusCode.WRONG_CREDENTIALS;
            return ResponseEntity
                    .status(status.getHttpCode())
                    .body(new ErrorResponse(status));
        }

        return ResponseEntity.ok(new UserInfoResponse(userService.getCurrentUserInfo()));
    }

    /**
     * Выйти из аккаунта
     */
    @GetMapping("/logout")
    public EmptyResponse logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(null);
        rememberMeServices.loginFail(request, response);
        return new EmptyResponse();
    }
}
