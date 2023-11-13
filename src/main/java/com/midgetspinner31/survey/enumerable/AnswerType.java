package com.midgetspinner31.survey.enumerable;

import com.midgetspinner31.survey.db.entity.answers.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum AnswerType {
    text(TextAnswer.class),
    single_choice(SingleChoiceAnswer.class),
    multiple_choice(MultipleChoiceAnswer.class),
    slider(SliderAnswer.class);

    Class<? extends QuestionAnswer> answerClass;
}
