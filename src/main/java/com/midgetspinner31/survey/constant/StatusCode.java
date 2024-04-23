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
    INCORRECT_DATE(7, 400, "Некорректная дата"),
    RESPONDENT_RESTRICTIONS_NOT_MATCHED(8, 403, "Вы не подходите по критериям данного опроса/интервью"),

    // user account
    EMAIL_IN_USE(100, 400, "Адрес электронной почты уже используется"),
    PHONE_IN_USE(101, 400, "Номер телефона уже используется"),
    WRONG_CREDENTIALS(102, 400, "Неправильный логин или пароль"),
    USER_NOT_FOUND(103, 404, "Пользователь не найден"),

    // survey
    SURVEY_NOT_FOUND(200, 404, "Опрос не найден"),
    SURVEY_ANSWER_VALIDATION_ERROR(201, 400, "Неверный формат ответа на опрос"),
    SURVEY_ANSWER_NOT_FOUND(202, 404, "Ответ на опрос не найден"),
    QUESTION_NOT_FOUND(203, 404, "Вопрос не найден"),
    RESPONDENT_FIELD_INVALID(200, 400, "Некорректно указано одно из полей данных о респонденте"),

    // interview
    INTERVIEW_NOT_FOUND(300, 404, "Интервью не найдено"),
    INTERVIEW_INVALID_TIME(305, 400, "Некорректное время интервью"),
    INTERVIEW_SLOT_NOT_FOUND(301, 404, "Слот интервью не найден"),
    INTERVIEW_SLOT_ALREADY_ACQUIRED(302, 403, "Слот интервью уже занят"),
    INTERVIEW_SLOT_ENDED(303, 400, "Регистрация на слот интервью закончилась"),
    INTERVIEW_SLOT_INVALID_TIME(304, 400, "Некорректное время слота"),

    // wallet
    WALLET_NOT_FOUND(400, 404, "Для пользователя не найдено кошельков"),
    WALLET_LOW_BALANCE(401, 400, "Недостаточно средств на кошельке"),

    // rating
    RATING_NOT_ALLOWED(500, 403, "Текущий пользователь не может оценить данное интервью"),
    RATING_TOO_EARLY(501, 400, "Нельзя оценить данное интервью, потому что оно ещё не закончилось"),
    RATING_NOT_FOUND(502, 404, "Оценка не найдена");

    int code;
    int httpCode;
    String message;
}
