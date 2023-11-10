package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.dto.QuestionInfo;
import com.midgetspinner31.survey.enumerable.AnswerType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Вопрос
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {

    /**
     * Текст вопроса
     */
    String text;
    /**
     * Обязательный вопрос
     */
    Boolean required;

    /**
     * Тип ответа
     */
    @Field("answer_type")
    AnswerType answerType;

    /**
     * Ограничения на ответ
     */
    Restrictions restrictions;

    public QuestionInfo toQuestionInfo() {
        return new QuestionInfo(text, required, answerType, restrictions.toRestrictionsInfo());
    }
}
