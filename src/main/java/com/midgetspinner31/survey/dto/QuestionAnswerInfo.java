package com.midgetspinner31.survey.dto;

import com.midgetspinner31.survey.db.entity.answers.Answer;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionAnswerInfo {

    Answer answer;
}
