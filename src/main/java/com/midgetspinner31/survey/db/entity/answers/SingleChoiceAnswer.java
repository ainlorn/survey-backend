package com.midgetspinner31.survey.db.entity.answers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleChoiceAnswer extends QuestionAnswer {
    Integer choice;
}
