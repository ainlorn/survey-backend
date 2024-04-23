package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.enumerable.InterviewResult;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ratings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rating {
    @Id
    String id;

    String slotId;

    String targetUserId;

    String sourceUserId;

    InterviewResult interviewResult;

    Integer rating;

    String comment;
}
