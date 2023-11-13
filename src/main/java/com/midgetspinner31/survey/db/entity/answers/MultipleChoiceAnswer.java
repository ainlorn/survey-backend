package com.midgetspinner31.survey.db.entity.answers;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultipleChoiceAnswer extends QuestionAnswer {
    @NotNull
    List<@NotNull Integer> choices;
}
