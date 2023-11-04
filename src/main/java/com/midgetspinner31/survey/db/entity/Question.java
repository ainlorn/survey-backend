package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.dto.QuestionInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String answerType;
    Restrictions restrictions;

    public QuestionInfo toQuestionInfo() {
        return new QuestionInfo(text, required, answerType, restrictions.toRestrictionsInfo());
    }
}
