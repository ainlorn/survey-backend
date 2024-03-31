package com.midgetspinner31.survey.db.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("interviews")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Interview {
    @Id
    String id;

    String name;

    String description;

    List<String> interviewTopics;

    String creatorId;

    Date creationDate;

    Date startDate;

    Date endDate;

    RespondentRestrictions respondentRestrictions;
}
