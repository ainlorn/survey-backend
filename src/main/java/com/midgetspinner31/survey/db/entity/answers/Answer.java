package com.midgetspinner31.survey.db.entity.answers;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "answerType", include = JsonTypeInfo.As.PROPERTY, visible = true)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = TextAnswer.class, name = "text"),
        @JsonSubTypes.Type(value = SingleChoiceAnswer.class, name = "single_choice"),
        @JsonSubTypes.Type(value = MultipleChoiceAnswer.class, name = "multiple_choice"),
        @JsonSubTypes.Type(value = SliderAnswer.class, name = "slider")
})
@Getter
@Setter
public class Answer {
    private String answerType;

}
