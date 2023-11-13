package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.db.entity.answers.QuestionAnswer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document("survey_answers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveyAnswer {
    @Id
    String id;

    @Field("survey_id")
    String surveyId;

    @Field("answered_at")
    Date answeredAt;

    @Field("respondent_id")
    String respondentId;

    @Field("polling_time")
    Integer pollingTime;
    List<QuestionAnswer> answers;
}
