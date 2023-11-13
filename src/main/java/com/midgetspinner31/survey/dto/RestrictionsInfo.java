package com.midgetspinner31.survey.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestrictionsInfo {
    Integer min;
    Integer max;
    Integer maxLength;
    List<String> choices;
}
