package com.midgetspinner31.survey.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum StatusCode {
    UNKNOWN(-1, 500, "Неизвестная ошибка"),
    OK(0, 200, null),
    VALIDATION_ERROR(1, 400, "Ошибка валидации"),
    METHOD_NOT_ALLOWED(2, 405, "Метод не разрешён"),
    MISSING_BODY(3, 400, "Отсутствует тело запроса"),
    ACCESS_DENIED(4, 403, "Доступ к ресурсу запрещён"),
    UNAUTHORIZED(5, 401, "Для доступа к этому ресурсу необходимо войти"),
    OPERATION_NOT_ALLOWED(6, 400, "Запрещённая операция"),

    // user account
    EMAIL_IN_USE(100, 400, "Адрес электронной почты уже используется"),
    PHONE_IN_USE(101, 400, "Номер телефона уже используется"),
    WRONG_CREDENTIALS(102, 400, "Неправильный логин или пароль"),
    USER_NOT_FOUND(103, 404, "Пользователь не найден"),

    // survey
    SURVEY_NOT_FOUND(200, 404, "Опрос не найден"),
    SURVEY_ANSWER_VALIDATION_ERROR(201, 400, "Неверный формат ответа на опрос"),
    SURVEY_ANSWER_NOT_FOUND(202, 404, "Ответ на опрос не найден"),
    QUESTION_NOT_FOUND(203, 404, "Вопрос не найден");

    int code;
    int httpCode;
    String message;
}
