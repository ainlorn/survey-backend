package com.midgetspinner31.survey.db.entity.answers;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SliderAnswer extends QuestionAnswer {
    @NotNull
    Integer value;
}
