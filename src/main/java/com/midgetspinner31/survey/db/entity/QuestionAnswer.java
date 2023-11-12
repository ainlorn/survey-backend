package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.db.entity.answers.Answer;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionAnswer {
    Answer answer;
}
