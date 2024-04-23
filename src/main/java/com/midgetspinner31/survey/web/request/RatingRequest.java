package com.midgetspinner31.survey.web.request;

import com.midgetspinner31.survey.enumerable.InterviewResult;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingRequest extends BaseRequest {
    @Setter
    @NotNull
    InterviewResult interviewResult;

    @Setter
    @NotNull
    @Min(1)
    @Max(10)
    Integer rating;

    @NotNull
    @Size(max=10000)
    String comment;

    public void setComment(String comment) {
        this.comment = trim(comment);
    }
}
