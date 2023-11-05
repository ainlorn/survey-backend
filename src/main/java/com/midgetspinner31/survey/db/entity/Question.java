package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.dto.QuestionInfo;
import com.midgetspinner31.survey.enumerable.AnswerType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {
    // TODO: переделать схему. В Question не должно быть поле question
    String text;
    Boolean required;
    @Field("answer_type")
    AnswerType answerType;
    Restrictions restrictions;

    public QuestionInfo toQuestionInfo() {
        return new QuestionInfo(text, required, answerType, restrictions.toRestrictionsInfo());
    }
}
