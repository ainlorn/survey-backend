package com.midgetspinner31.survey.db.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("interview_slots")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterviewSlot {
    @Id
    String id;

    String interviewId;

    String respondentId;

    Date startDate;

    Date endDate;
}
