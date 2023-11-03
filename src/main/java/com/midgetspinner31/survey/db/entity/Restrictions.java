package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.dto.RestrictionsInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Restrictions {
    Double min;
    Double max;
    Integer maxLength;
    List<String> choices;

    public RestrictionsInfo toRestrictionsInfo() {
        return new RestrictionsInfo(min, max, maxLength, choices);
    }
}
