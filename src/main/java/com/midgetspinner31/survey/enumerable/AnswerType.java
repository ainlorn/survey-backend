package com.midgetspinner31.survey.enumerable;

// TODO: вопрос:
// Если сделаем enum по правильному (uppercase) - будем ли переделывать схему,
// чтобы в теле запроса тоже передавалась строка с верхним регистром?
public enum AnswerType {
    text,
    single_choice,
    multiple_choice,
    slider
}
